package com.github.anilople.javalua.chunk;

import static com.github.anilople.javalua.chunk.BinaryChunkConstants.*;

import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lua.h">lua.h</a>
 */
@Data
public class BinaryChunk implements Encodable, Decodable {

  Header header;
  byte sizeUpvalues;
  Prototype mainFunc;

  @Override
  public byte[] encode() {
    byte[] headerBytes = this.header.encode();
    byte[] mainFuncBytes = this.mainFunc.encode();
    return ArrayUtils.concat(headerBytes, this.sizeUpvalues, mainFuncBytes);
  }

  @Override
  public void decode(InputStream inputStream) throws IOException {
    this.header = new Header();
    this.header.decode(inputStream);

    this.sizeUpvalues = (byte) inputStream.read();

    this.mainFunc = new Prototype();
    this.mainFunc.decode(inputStream);

    assert inputStream.read() == -1;
  }

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L197">ldump.c#L197</a> for header's dump
   */
  @Data
  public static class Header implements Encodable, Decodable {

    public static final Header INSTANCE = new Header();
    public static final int SIZE = INSTANCE.encode().length;

    byte[] luaSignature = LUA_SIGNATURE;
    byte luacVersion = LUAC_VERSION;
    byte luacFormat = LUAC_FORMAT;
    byte[] luacData = LUAC_DATA;
    byte sizeOfInstruction = SIZE_OF_INSTRUCTION;
    byte sizeOfLuaInteger = SIZE_OF_LUA_INTEGER;
    byte sizeOfLuaNumber = SIZE_OF_LUA_NUMBER;
    long luacInt = LUAC_INT;
    double luacNum = LUAC_NUM;

    @Override
    public byte[] encode() {
      try {
        return ByteUtils.encode(this);
      } catch (IOException e) {
        throw new IllegalStateException("cannot dump " + this, e);
      }
    }

    @Override
    public void decode(InputStream inputStream) throws IOException {
      this.luaSignature = inputStream.readNBytes(LUA_SIGNATURE.length);
      this.luacVersion = (byte) inputStream.read();
      this.luacFormat = (byte) inputStream.read();
      this.luacData = inputStream.readNBytes(LUAC_DATA.length);
      this.sizeOfInstruction = (byte) inputStream.read();
      this.sizeOfLuaInteger = (byte) inputStream.read();
      this.sizeOfLuaNumber = (byte) inputStream.read();
      this.luacInt = ByteUtils.decodeLong(inputStream.readNBytes(8));
      this.luacNum = ByteUtils.decodeDouble(inputStream.readNBytes(8));
    }
  }

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L539">lobject.h#L539 typedef struct Proto</a>
   */
  @Data
  public static class Prototype implements Encodable, Decodable {

    /**
     * 源文件名
     */
    LuaString source = LuaString.NULL;
    /**
     * 起始行号
     */
    int lineDefined;
    /**
     * 结束行号
     */
    int lastLineDefined;
    /**
     * 固定参数个数
     */
    byte numParams;

    byte isVararg;
    /**
     * 寄存器数量
     */
    byte maxStackSize;
    /**
     * 指令表
     */
    int[] code = new int[0];
    /**
     * 常量表
     */
    // TODO, constants
    Constants constants;

    /**
     * Upvalue表
     */
    Upvalue[] upvalues = new Upvalue[0];
    /**
     * 子函数原型表
     */
    Prototype[] protos = new Prototype[0];

    /**
     * 行号表
     */
    int[] lineInfo = new int[0];
    /**
     * 局部变量表
     */
    LocVar[] locVars = new LocVar[0];
    /**
     * Upvalue名列表
     */
    String[] upvalueNames = new String[0];

    /**
     * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L179">
     *   ldump.c#L179 static void dumpFunction (DumpState *D, const Proto *f, TString *psource)
     *   </a>
     */
    @Override
    public byte[] encode() {
      try {
        return encodeWithIOException();
      } catch (IOException e) {
        throw new IllegalStateException("cannot happen that", e);
      }
    }

    private byte[] encodeWithIOException() throws IOException {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      outputStream.write(this.source.encode());
      outputStream.write(ByteUtils.encodeInt(this.lineDefined));
      outputStream.write(ByteUtils.encodeInt(this.lastLineDefined));

      outputStream.write(this.numParams);
      outputStream.write(this.isVararg);
      outputStream.write(this.maxStackSize);

      // dumpCode https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L100
      /*
        static void dumpCode (DumpState *D, const Proto *f) {
          dumpInt(D, f->sizecode);
          dumpVector(D, f->code, f->sizecode);
        }
       */
      outputStream.write(this.code.length);
      outputStream.write(ArrayUtils.toByteArray(this.code));

      // dump constants

      outputStream.write(this.upvalues.length);
      outputStream.write(Encodable.encode(this.upvalues));

      outputStream.write(this.protos.length);
      outputStream.write(Encodable.encode(this.protos));

      // dump debug
      return outputStream.toByteArray();
    }

    @Override
    public void decode(InputStream inputStream) {

    }

    public static class LuaString implements Encodable, Decodable {
      public static final LuaString NULL = new LuaString();
      private static final byte[] ZERO = new byte[]{0};
      byte first;
      byte[] bytes;

      @Override
      public byte[] encode() {
        if (0 == first) {
          return ZERO;
        } else if ((~first) != 0) {
          // <= 0xFD
          // if first == 0xFF, ~first => 0x00
          return ArrayUtils.concat((byte) (first + 1), bytes);
        } else {
          // >= 0xFE
          byte[] secondPart = ByteUtils.encodeLong(bytes.length + 1);
          return ArrayUtils.concat(first, secondPart, bytes);
        }
      }

      @Override
      public void decode(InputStream inputStream) throws IOException {
        byte first = (byte) inputStream.read();
        if (0 == first) {
          // do nothing
        } else if ((~first) != 0) {
          // <= 0xFD
          // if first == 0xFF, ~first => 0x00
          int length = Byte.toUnsignedInt(first) - 1;
          bytes = inputStream.readNBytes(length);
        } else {
          // >= 0xFE
          long length = ByteUtils.decodeLong(inputStream.readNBytes(8)) - 1;
          bytes = inputStream.readNBytes((int) length);
        }
      }
    }

    public static class BasicInfo {}

    public static class Bytecodes {}

    public static class Constants {
      byte tag;

      interface Tag {
        byte NIL = 0x00;
        byte BOOLEAN = 0x01;
        byte NUMBER = 0x03;
        byte INTEGER = 0x13;
        byte SHORT_STRING = 0x04;
        byte LONG_STRING = 0x01;
      }
    }

    /**
     * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L502">lobject.h#L502 Upvaldesc</a>
     */
    @Data
    public static class Upvalue implements Encodable, Decodable {
      byte instack;
      byte idex;

      @Override
      public void decode(InputStream inputStream) throws IOException {

      }

      @Override
      public byte[] encode() {
        return new byte[0];
      }
    }

    public static class DebugInfo {}

    public static class SubFunctions {

      List<Prototype> functions;
    }

    @Data
    public static class LocVar {
      String varName;
      int startPC;
      int endPC;
    }
  }
}
