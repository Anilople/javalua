package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class Constant implements Encodable, Decodable {

  private static final byte[] NIL_BYTES = new byte[0];
  byte tag;
  LuaBoolean luaBoolean;
  LuaInteger luaInteger;
  LuaNumber luaNumber;
  LuaString luaString;

  public boolean isLuaBoolean() {
    return tag == Tag.BOOLEAN;
  }

  public boolean getLuaBooleanInJava() {
    return this.luaBoolean.value;
  }

  public boolean isLuaInteger() {
    return tag == Tag.INTEGER;
  }

  public long getLuaIntegerInJava() {
    return this.luaInteger.value;
  }

  public boolean isLuaNumber() {
    return tag == Tag.NUMBER;
  }

  public double getLuaNumberInJava() {
    return this.luaNumber.value;
  }

  public boolean isLuaString() {
    if (tag == Tag.SHORT_STRING) {
      return true;
    }
    if (tag == Tag.LONG_STRING) {
      return true;
    }
    return false;
  }

  public String getLuaStringInJava() {
    return this.luaString.toJavaString();
  }

  public boolean isLuaNil() {
    return tag == Tag.NIL;
  }

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
  public void decode(DecodeInputStream inputStream) {
    this.tag = inputStream.readByte();
    switch (this.tag) {
      case Tag.BOOLEAN:
        this.luaBoolean = new LuaBoolean();
        this.luaBoolean.decode(inputStream);
        break;
      case Tag.NUMBER:
        this.luaNumber = new LuaNumber();
        this.luaNumber.decode(inputStream);
        break;
      case Tag.INTEGER:
        this.luaInteger = new LuaInteger();
        this.luaInteger.decode(inputStream);
        break;
      case Tag.SHORT_STRING:
      case Tag.LONG_STRING:
        this.luaString = new LuaString();
        this.luaString.decode(inputStream);
        break;
      default:
        assert this.tag == Tag.NIL;
    }
  }

  @Override
  public String toString() {
    switch (tag) {
      case Tag.BOOLEAN:
        return this.getClass().getSimpleName() + "[" + this.getLuaBoolean() + "]";
      case Tag.NUMBER:
        return this.getClass().getSimpleName() + "[" + this.getLuaNumber() + "]";
      case Tag.INTEGER:
        return this.getClass().getSimpleName() + "[" + this.getLuaInteger() + "]";
      case Tag.SHORT_STRING:
      case Tag.LONG_STRING:
        return this.getClass().getSimpleName() + "[" + this.getLuaString() + "]";
      default:
        assert tag == Tag.NIL;
        return this.getClass().getSimpleName() + "[" + Tag.NIL + "]";
    }
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
}
