package com.github.anilople.javalua.chunk;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wxq
 */
interface Decodable {
  void decode(InputStream inputStream) throws IOException;
}
