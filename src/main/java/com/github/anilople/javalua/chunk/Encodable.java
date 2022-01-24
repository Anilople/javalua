package com.github.anilople.javalua.chunk;

/**
 * 编码，和 dump 对应
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c">ldump.c</a>
 */
public interface Encodable {

  byte[] encode();

  static byte[] encode(Encodable[] encodables) {
    EncodeOutputStream outputStream = new EncodeOutputStream();
    for (Encodable encodable : encodables) {
      byte[] bytes = encodable.encode();
      outputStream.writeBytes(bytes);
    }
    return outputStream.toByteArray();
  }
}
