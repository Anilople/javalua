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

  @Data
  public static class Prototype {

    String source;
    int LineDefined;
    int LastLineDefined;
    byte numParams;
    byte isVararg;
    byte maxStackSize;
    int[] code;
    // TODO, constants
    Upvalue[] upvalues;
    Prototype[] protos;
    int[] lineInfo;
    LocVar[] locVars;
    String[] UpvalueNames;

    public static class BasicInfo {}

    public static class Bytecodes {}

    public static class Constants {}

    public static class Upvalue {}

    public static class DebugInfo {}

    public static class SubFunctions {

      List<Prototype> functions;
    }

    public static class LocVar {}
  }
}
