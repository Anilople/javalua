package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Lua;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
class LuaInteger implements Encodable, Decodable {

  long value;

  @Override
  public void decode(DecodeInputStream inputStream) {
    byte[] bytes = inputStream.readNBytes(Lua.INTEGER);
    this.value = ByteUtils.decodeLong(bytes);
  }

  @Override
  public byte[] encode() {
    return ByteUtils.encodeLong(this.value);
  }
}
