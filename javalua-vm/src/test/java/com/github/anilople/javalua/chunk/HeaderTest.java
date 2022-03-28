package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class HeaderTest {

  @Test
  void testHeaderDump() {
    byte[] bytes = Header.INSTANCE.encode();
    assertEquals(33, bytes.length);
  }

  @Test
  void testHeaderUndumpSelf() throws IOException {
    var bytes = Header.INSTANCE.encode();
    var actualHeader = ByteUtils.decode(bytes, Header.class);
    assertArrayEquals(bytes, actualHeader.encode());
  }
}
