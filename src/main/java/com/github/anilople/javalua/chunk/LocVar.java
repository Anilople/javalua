package com.github.anilople.javalua.chunk;

import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LocVar implements Encodable, Decodable {
  LuaString varName;
  int startPC;
  int endPC;

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.varName = new LuaString();
    this.varName.decode(inputStream);
    this.startPC = inputStream.readInt();
    this.endPC = inputStream.readInt();
  }

  @Override
  public byte[] encode() {
    EncodeOutputStream outputStream = new EncodeOutputStream();
    outputStream.writeBytes(this.varName.encode());
    outputStream.writeInt(this.startPC);
    outputStream.writeInt(this.endPC);
    return outputStream.toByteArray();
  }
}
