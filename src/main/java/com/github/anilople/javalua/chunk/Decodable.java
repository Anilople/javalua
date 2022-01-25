package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * 解码，和undump对应
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lundump.c">lundump.c</a>
 */
interface Decodable {

  void decode(DecodeInputStream inputStream) throws IOException;

  static void decode(Decodable[] decodables, DecodeInputStream inputStream) throws IOException {
    for (Decodable decodable : decodables) {
      decodable.decode(inputStream);
    }
  }
}
