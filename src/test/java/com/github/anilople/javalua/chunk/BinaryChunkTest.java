package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.ResourceContentConstants;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class BinaryChunkTest {



  BinaryChunkTest() throws IOException {}

  @Test
  void testHelloWorldLuac53OutSize() {
    assertEquals(151, ResourceContentConstants.ch02.helloWorldLuac53Out.length);
  }

  @Test
  void testHelloWorldLuac54OutSize() {
    assertEquals(117, ResourceContentConstants.ch02.helloWorldLuac54Out.length);
  }

  @Test
  void testBinaryChunkEncodeCase1() {
    var binaryChunk = new BinaryChunk();
    binaryChunk.header = Header.INSTANCE;
    binaryChunk.sizeUpvalues = 2;
    binaryChunk.mainFunc = new Prototype();

    binaryChunk.encode();
  }

    @Test
    void testhelloWorldLuac53OutDecode() throws IOException {
      var binaryChunk = new BinaryChunk();
      DecodeInputStream inputStream = new DecodeInputStream(ResourceContentConstants.ch02.helloWorldLuac53Out);
      binaryChunk.decode(inputStream);
    }
}
