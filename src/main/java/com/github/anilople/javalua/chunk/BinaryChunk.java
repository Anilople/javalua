package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ArrayUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lua.h">lua.h</a>
 */
@Data
public class BinaryChunk {

  final Header header;
  final Prototype prototype;

  @Data
  public static class Header {

    public static final Header INSTANCE = new Header();

    /**
     * 魔数，类似Java class文件开头的0xCAFEBABE
     *
     * lua的魔数也是4个字节，ESC，L，u，a的ASCII码，16进制是0x1B4C7561
     */
    byte[] signature = {0X1B, 0x4C, 0x75, 0x61};

    byte version;
    byte format;
    byte[] luacData = new byte[6];
    byte cintSize;
    byte sizetSize;
    byte instructionSize;
    byte luaIntegerSize;
    byte luaNumberSize;
    /**
     * int64，用来检测大小端
     */
    long luacInt;
    /**
     * float64
     */
    double luacNum;
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
