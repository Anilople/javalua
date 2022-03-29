package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */
import static com.github.anilople.javalua.constant.ASCIIConstants.CR;
import static com.github.anilople.javalua.constant.ASCIIConstants.ESC;
import static com.github.anilople.javalua.constant.ASCIIConstants.L;
import static com.github.anilople.javalua.constant.ASCIIConstants.LF;
import static com.github.anilople.javalua.constant.ASCIIConstants.a;
import static com.github.anilople.javalua.constant.ASCIIConstants.u;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.C;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import lombok.Data;

@Data
public class Header implements Encodable, Decodable {

  /**
   * 魔数，类似Java class文件开头的0xCAFEBABE
   * <p>
   * lua的魔数也是4个字节，ESC，L，u，a的ASCII码，16进制是0x1B4C7561
   */
  public static final byte[] LUA_SIGNATURE = {ESC, L, u, a};

  static final byte LUAC_VERSION = Version.INSTANCE.encode();
  static final byte LUAC_FORMAT = 0;
  public static final byte[] LUAC_DATA = new byte[] {0x19, (byte) 0x93, CR, LF, 0x1a, LF};

  /**
   * Lua虚拟机指令占用的字节数
   */
  static final byte SIZE_OF_INSTRUCTION = 4;
  /**
   * Lua整数占用的字节数
   */
  static final byte SIZE_OF_LUA_INTEGER = 8;
  /**
   * Lua浮点数占用的字节数
   */
  static final byte SIZE_OF_LUA_NUMBER = 8;

  /**
   * int64，0x5678 用来检测大小端
   */
  static final long LUAC_INT = 0x5678;
  /**
   * float64
   */
  static final double LUAC_NUM = 370.5;

  public static final Header INSTANCE = new Header();
  /**
   * lua 5.4 中，header的大小是 31 bytes
   *
   * lua 5.3 中 是 33 bytes
   */
  public static final int SIZE = 33;

  byte[] luaSignature = LUA_SIGNATURE;
  byte luacVersion = LUAC_VERSION;
  byte luacFormat = LUAC_FORMAT;
  byte[] luacData = LUAC_DATA;
  byte sizeOfCInt = C.INT;
  byte sizeOfCSizeT = C.SIZE_T;
  byte sizeOfInstruction = SIZE_OF_INSTRUCTION;
  byte sizeOfLuaInteger = SIZE_OF_LUA_INTEGER;
  byte sizeOfLuaNumber = SIZE_OF_LUA_NUMBER;
  long luacInt = LUAC_INT;
  double luacNum = LUAC_NUM;

  void check() {
    if (!this.equals(INSTANCE)) {
      throw new IllegalStateException("header is invalid " + this);
    }
  }

  @Override
  public byte[] encode() {
    try {
      return ByteUtils.encode(this);
    } catch (IOException e) {
      throw new IllegalStateException("cannot dump " + this, e);
    }
  }

  @Override
  public void decode(DecodeInputStream inputStream) {
    this.luaSignature = inputStream.readNBytes(LUA_SIGNATURE.length);
    this.luacVersion = inputStream.readByte();
    this.luacFormat = inputStream.readByte();
    this.luacData = inputStream.readNBytes(LUAC_DATA.length);
    this.sizeOfCInt = inputStream.readByte();
    this.sizeOfCSizeT = inputStream.readByte();
    this.sizeOfInstruction = inputStream.readByte();
    this.sizeOfLuaInteger = inputStream.readByte();
    this.sizeOfLuaNumber = inputStream.readByte();
    this.luacInt = inputStream.readLong();
    this.luacNum = inputStream.readDouble();
  }
}
