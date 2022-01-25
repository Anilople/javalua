package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.chunk.Constants.Tag;
import java.io.IOException;
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
    this.tag = inputStream.readByte();
    switch (this.tag) {
      case Tag.BOOLEAN:
        this.luaBoolean.decode(inputStream);
        break;
      case Tag.NUMBER:
        this.luaNumber.decode(inputStream);
        break;
      case Tag.INTEGER:
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
}
