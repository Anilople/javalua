package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */

import com.github.anilople.javalua.util.ArrayUtils;
import java.io.IOException;
import java.util.List;
import lombok.Data;

/**
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L539">lobject.h#L539
 * typedef struct Proto</a>
 */
@Data
public class Prototype implements Encodable, Decodable {

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
  Constants constants = new Constants();

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
   * ldump.c#L179 static void dumpFunction (DumpState *D, const Proto *f, TString *psource)
   * </a>
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
    EncodeOutputStream outputStream = new EncodeOutputStream();
    outputStream.writeBytes(this.source.encode());

    outputStream.writeInt(this.lineDefined);
    outputStream.writeInt(this.lastLineDefined);

    outputStream.writeByte(this.numParams);
    outputStream.writeByte(this.isVararg);
    outputStream.writeByte(this.maxStackSize);

    // dumpCode
    // https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L100
      /*
       static void dumpCode (DumpState *D, const Proto *f) {
         dumpInt(D, f->sizecode);
         dumpVector(D, f->code, f->sizecode);
       }
      */
    outputStream.writeInt(this.code.length);
    outputStream.writeBytes(ArrayUtils.toByteArray(this.code));

    // dump constants
    outputStream.writeBytes(this.constants.encode());

    outputStream.writeInt(this.upvalues.length);
    outputStream.writeBytes(Encodable.encode(this.upvalues));

    outputStream.writeInt(this.protos.length);
    outputStream.writeBytes(Encodable.encode(this.protos));

    // dump debug
    return outputStream.toByteArray();
  }

  @Override
  public void decode(DecodeInputStream inputStream) {
  }


  public static class BasicInfo {

  }

  public static class Bytecodes {

  }

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L502">lobject.h#L502
   * Upvaldesc</a>
   */
  @Data
  public static class Upvalue implements Encodable, Decodable {

    byte instack;
    byte idex;

    @Override
    public void decode(DecodeInputStream inputStream) throws IOException {
    }

    @Override
    public byte[] encode() {
      return new byte[0];
    }
  }

  public static class DebugInfo {

  }

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
