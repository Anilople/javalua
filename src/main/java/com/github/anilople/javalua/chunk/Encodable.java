package com.github.anilople.javalua.chunk;

/**
 * 编码，和 dump 对应
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/ldump.c">ldump.c</a>
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
