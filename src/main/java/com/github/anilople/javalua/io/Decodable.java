package com.github.anilople.javalua.io;

import com.github.anilople.javalua.util.ReflectionUtils;
import java.io.IOException;

/**
 * 解码，和undump对应
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lundump.c">lundump.c</a>
 */
public interface Decodable {

  void decode(DecodeInputStream inputStream);

  static <T extends Decodable> void decode(
      Class<T> clazz, T[] decodables, DecodeInputStream inputStream) {
    int length = decodables.length;
    for (int i = 0; i < length; i++) {
      T t = ReflectionUtils.newInstance(clazz);
      t.decode(inputStream);
      decodables[i] = t;
    }
  }
}
