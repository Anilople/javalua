package com.github.anilople.javalua.chunk;

import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lobject.h">lobject.h Upvaldesc</a>
 */
@Data
public class Upvalue implements Encodable, Decodable {
  byte instack;
  byte idx;
  byte kind;

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.instack = inputStream.readByte();
    this.idx = inputStream.readByte();
    this.kind = inputStream.readByte();
  }

  @Override
  public byte[] encode() {
    return new byte[] {this.instack, this.idx, this.kind};
  }
}