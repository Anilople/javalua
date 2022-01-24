package com.github.anilople.javalua.chunk;

import static com.github.anilople.javalua.constant.ASCIIConstants.CR;
import static com.github.anilople.javalua.constant.ASCIIConstants.ESC;
import static com.github.anilople.javalua.constant.ASCIIConstants.L;
import static com.github.anilople.javalua.constant.ASCIIConstants.LF;
import static com.github.anilople.javalua.constant.ASCIIConstants.a;
import static com.github.anilople.javalua.constant.ASCIIConstants.u;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lundump.h">lundump.h</a>
 */
interface BinaryChunkConstants {

  /**
   * 魔数，类似Java class文件开头的0xCAFEBABE
   * <p>
   * lua的魔数也是4个字节，ESC，L，u，a的ASCII码，16进制是0x1B4C7561
   */
  byte[] LUA_SIGNATURE = {ESC, L, u, a};

  byte LUAC_VERSION = Version.INSTANCE.encode();
  byte LUAC_FORMAT = 0;
  /**
   * "\x19\x93\r\n\x1a\n"
   *
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lundump.h#L16">lundump.h#L16</a>
   */
  byte[] LUAC_DATA = new byte[]{0x19, (byte) 0x93, CR, LF, 0x1a, LF};

  /**
   * c语言 int 占用的字节数
   */
  byte C_INT_SIZE = 4;
  /**
   * c语言 size_t 占用的字节数
   */
  byte C_SIZE_T_SIZE = 8;

  /**
   * Lua虚拟机指令占用的字节数
   */
  byte SIZE_OF_INSTRUCTION = 4;
  /**
   * Lua整数占用的字节数
   */
  byte SIZE_OF_LUA_INTEGER = 8;
  /**
   * Lua浮点数占用的字节数
   */
  byte SIZE_OF_LUA_NUMBER = 8;

  /**
   * int64，0x5678 用来检测大小端
   *
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lundump.h#L18">LUAC_INT</a>
   */
  long LUAC_INT = 0x5678;
  /**
   * float64
   */
  double LUAC_NUM = 370.5;
}
