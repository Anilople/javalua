package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Lua;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ByteUtils;
import lombok.Data;

/**
 * @author wxq
 */
@Data
class LuaNumber implements Encodable, Decodable {

  double value;

  @Override
  public void decode(DecodeInputStream inputStream) {
    byte[] bytes = inputStream.readNBytes(Lua.NUMBER);
    this.value = ByteUtils.decodeDouble(bytes);
  }

  @Override
  public byte[] encode() {
    return ByteUtils.encodeDouble(this.value);
  }
}
