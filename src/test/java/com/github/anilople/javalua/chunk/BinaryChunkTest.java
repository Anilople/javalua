package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.ResourceReadUtils;
import com.github.anilople.javalua.chunk.BinaryChunk.Header;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class BinaryChunkTest {

  private final byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes(
      "ch02/hello_world.luac54.out");

  BinaryChunkTest() throws IOException {
  }

  @Test
  void testHelloWorldLuac54OutSize() {
    assertEquals(117, helloWorldLuac54Out.length);
  }

  @Test
  void testHelloWorldLuac54OutSignature() {
    for (int i = 0; i < 4; i++) {
      assertEquals(Header.INSTANCE.signature[i], helloWorldLuac54Out[i]);
    }
    assertArrayEquals("Lua".getBytes(StandardCharsets.UTF_8),
        new byte[]{helloWorldLuac54Out[1], helloWorldLuac54Out[2], helloWorldLuac54Out[3]});
  }

}