package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.SizeConstants.Lua;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;

/**
 * @author wxq
 */
public class LuaInteger implements Encodable, Decodable {

  private long value;

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    byte[] bytes = inputStream.readNBytes(Lua.INTEGER);
    this.value = ByteUtils.decodeLong(bytes);
  }

  @Override
  public byte[] encode() {
    return ByteUtils.encodeLong(this.value);
  }
}
