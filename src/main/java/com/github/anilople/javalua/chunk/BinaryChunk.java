package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ArrayUtils;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lua.h">lua.h</a>
 */
@Data
public class BinaryChunk implements Encodable, Decodable {

  Header header;
  byte sizeUpvalues;
  Prototype mainFunc;

  /**
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L213">ldump.c#L213
   * luaU_dump</a>
   */
  @Override
  public byte[] encode() {
    byte[] headerBytes = this.header.encode();
    byte[] mainFuncBytes = this.mainFunc.encode();
    return ArrayUtils.concat(headerBytes, this.sizeUpvalues, mainFuncBytes);
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.header = new Header();
    this.header.decode(inputStream);

    this.sizeUpvalues = inputStream.readByte();

    this.mainFunc = new Prototype();
    this.mainFunc.decode(inputStream);

    assert inputStream.readByte() == -1;
  }


}
