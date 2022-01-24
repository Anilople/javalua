package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * @author wxq
 */
public class Constants implements Encodable, Decodable {

  Constant[] constants = new Constant[0];

  @Override
  public byte[] encode() {
    EncodeOutputStream outputStream = new EncodeOutputStream();
    outputStream.writeInt(this.constants.length);
    for (Constant constant : this.constants) {
      byte[] bytes = constant.encode();
      outputStream.writeBytes(bytes);
    }
    return outputStream.toByteArray();
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {

  }

  interface Tag {

    /**
     * lua nil 不存储
     */
    byte NIL = 0x00;
    /**
     * lua boolean 字节（0,1）
     */
    byte BOOLEAN = 0x01;
    /**
     * lua number Lua浮点数
     */
    byte NUMBER = 0x03;
    /**
     * lua integer Lua整数
     */
    byte INTEGER = 0x13;
    /**
     * lua string Lua短字符串
     */
    byte SHORT_STRING = 0x04;
    /**
     * lua string Lua长字符串
     */
    byte LONG_STRING = 0x14;
  }

  public static class Constant implements Encodable, Decodable {

    private static final byte[] NIL_BYTES = new byte[0];
    byte tag;
    LuaBoolean luaBoolean;
    LuaInteger luaInteger;
    LuaNumber luaNumber;
    LuaString luaString;

    @Override
    public byte[] encode() {
      switch (tag) {
        case Tag.BOOLEAN:
          return this.luaBoolean.encode();
        case Tag.NUMBER:
          return this.luaNumber.encode();
        case Tag.INTEGER:
          return this.luaInteger.encode();
        case Tag.SHORT_STRING:
        case Tag.LONG_STRING:
          return this.luaString.encode();
        default:
          // write nothing
          assert tag == Tag.NIL;
          return NIL_BYTES;
      }
    }

    @Override
    public void decode(DecodeInputStream inputStream) throws IOException {

    }
  }
}
