package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * @author wxq
 */
interface Decodable {

  void decode(DecodeInputStream inputStream) throws IOException;
}
