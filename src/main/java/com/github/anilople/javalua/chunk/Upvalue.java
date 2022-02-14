package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import lombok.Data;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lobject.h">lobject.h Upvaldesc</a>
 */
@Data
public class Upvalue implements Encodable, Decodable {
  byte instack;
  byte idx;

  // lua 5.4.3 有 kind 字段，5.3 没有
  // byte kind;

  @Override
  public void decode(DecodeInputStream inputStream) {
    this.instack = inputStream.readByte();
    this.idx = inputStream.readByte();
  }

  @Override
  public byte[] encode() {
    return new byte[] {this.instack, this.idx};
  }
}
