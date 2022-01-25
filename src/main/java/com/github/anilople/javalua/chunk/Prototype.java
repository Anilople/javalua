package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */
import java.io.IOException;
import java.util.List;
import lombok.Data;

/**
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lobject.h">lobject typedef struct Proto</a>
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
  Code code = new Code();
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
    outputStream.writeBytes(this.code.encode());

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
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.source = new LuaString();
    this.source.decode(inputStream);
    this.lineDefined = inputStream.readInt();
    this.lastLineDefined = inputStream.readInt();
    this.numParams = inputStream.readByte();
    this.isVararg = inputStream.readByte();
    this.maxStackSize = inputStream.readByte();

    this.code = new Code();
    this.code.decode(inputStream);

    this.constants = new Constants();
    this.constants.decode(inputStream);

    {
      int length = inputStream.readInt();
      this.upvalues = new Upvalue[length];
      Decodable.decode(this.upvalues, inputStream);
    }

    {
      int length = inputStream.readInt();
      this.protos = new Prototype[length];
      Decodable.decode(this.protos, inputStream);
    }
  }

  public static class BasicInfo {}

  public static class Bytecodes {}

  public static class DebugInfo {}

  public static class SubFunctions {

    List<Prototype> functions;
  }
}
