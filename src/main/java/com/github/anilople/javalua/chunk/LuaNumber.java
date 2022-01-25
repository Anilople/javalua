package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Lua;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;

/**
 * @author wxq
 */
public class LuaNumber implements Encodable, Decodable {

  private double value;

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    byte[] bytes = inputStream.readNBytes(Lua.NUMBER);
    this.value = ByteUtils.decodeDouble(bytes);
  }

  @Override
  public byte[] encode() {
    return ByteUtils.encodeDouble(this.value);
  }
}
