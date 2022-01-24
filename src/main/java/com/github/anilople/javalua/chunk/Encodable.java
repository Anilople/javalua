package com.github.anilople.javalua.chunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author wxq
 */
public interface Encodable {

  /**
   * i.e dump
   *
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c">ldump.c</a>
   */
  byte[] encode();

  static byte[] encode(Encodable[] encodables) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (Encodable encodable : encodables) {
      byte[] bytes = encodable.encode();
      try {
        byteArrayOutputStream.write(bytes);
      } catch (IOException e) {
        throw new IllegalStateException("cannot happen", e);
      }
    }
    return byteArrayOutputStream.toByteArray();
  }
}
