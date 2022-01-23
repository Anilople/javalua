package com.github.anilople.javalua.chunk;

import static com.github.anilople.javalua.chunk.BinaryChunkConstants.*;

import java.util.List;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lua.h">lua.h</a>
 */
@Data
public class BinaryChunk implements Dumpable {

  final Header header;
  final byte sizeUpvalues;
  final Prototype mainFunc;

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L197">ldump.c#L197</a> for header's dump
   */
  @Data
  public static class Header implements Dumpable {

    public static final Header INSTANCE = new Header();
    public static final int SIZE = INSTANCE.dump().length;

    byte[] luaSignature = LUA_SIGNATURE;
    byte luacVersion = LUAC_VERSION;
    byte luacFormat = LUAC_FORMAT;
    byte[] luacData = LUAC_DATA;
    byte sizeOfInstruction = SIZE_OF_INSTRUCTION;
    byte sizeOfLuaInteger = SIZE_OF_LUA_INTEGER;
    byte sizeOfLuaNumber = SIZE_OF_LUA_NUMBER;
    long luacInt = LUAC_INT;
    double luacNum = LUAC_NUM;
  }

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L539">lobject.h#L539 typedef struct Proto</a>
   */
  @Data
  public static class Prototype {

    /**
     * 源文件名
     */
    String source;
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
    int[] code;
    /**
     * 常量表
     */
    // TODO, constants
    /**
     * Upvalue表
     */
    Upvalue[] upvalues;
    /**
     * 子函数原型表
     */
    Prototype[] protos;
    /**
     * 行号表
     */
    int[] lineInfo;
    /**
     * 局部变量表
     */
    LocVar[] locVars;
    /**
     * Upvalue名列表
     */
    String[] upvalueNames;

    public static class BasicInfo {}

    public static class Bytecodes {}

    public static class Constants {}

    /**
     * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lobject.h#L502">lobject.h#L502 Upvaldesc</a>
     */
    @Data
    public static class Upvalue {
      byte instack;
      byte idex;
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
