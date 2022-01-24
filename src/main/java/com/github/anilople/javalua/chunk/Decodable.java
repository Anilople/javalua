package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * 解码，和undump对应
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lundump.c">lundump.c</a>
 */
interface Decodable {

  void decode(DecodeInputStream inputStream) throws IOException;
}
