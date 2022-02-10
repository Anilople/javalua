package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ArrayUtils;
import java.io.IOException;
import java.util.Objects;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h">lua.h</a>
 */
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BinaryChunk that = (BinaryChunk) o;
    return sizeUpvalues == that.sizeUpvalues
        && Objects.equals(header, that.header)
        && Objects.equals(mainFunc, that.mainFunc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, sizeUpvalues, mainFunc);
  }
}
