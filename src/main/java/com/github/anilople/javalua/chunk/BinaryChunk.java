package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ArrayUtils;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h">lua.h</a>
 */
@Data
public class BinaryChunk implements Encodable, Decodable {

  Header header = new Header();
  byte sizeUpvalues;
  Prototype mainFunc = new Prototype();

  @Override
  public byte[] encode() {
    byte[] headerBytes = this.header.encode();
    byte[] mainFuncBytes = this.mainFunc.encode();
    return ArrayUtils.concat(headerBytes, this.sizeUpvalues, mainFuncBytes);
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.header.decode(inputStream);
    this.header.check();

    this.sizeUpvalues = inputStream.readByte();
    this.mainFunc.decode(inputStream);
  }
}
