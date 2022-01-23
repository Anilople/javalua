package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;

/**
 * @author wxq
 */
public interface Dumpable {

  default byte[] dump() {
    try {
      return ByteUtils.encode(this);
    } catch (IOException e) {
      throw new IllegalStateException("cannot dump " + this, e);
    }
  }
}
