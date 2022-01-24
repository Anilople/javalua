package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.ResourceReadUtils;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class BinaryChunkTest {

  /**
   * little endian
   */
  private final byte[] helloWorldLuac54Out =
      ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");

  BinaryChunkTest() throws IOException {}

  @Test
  void testHelloWorldLuac54OutSize() {
    assertEquals(117, helloWorldLuac54Out.length);
  }

  @Test
  void testBinaryChunkEncodeCase1() {
    var binaryChunk = new BinaryChunk();
    binaryChunk.header = Header.INSTANCE;
    binaryChunk.sizeUpvalues = 2;
    binaryChunk.mainFunc = new Prototype();

    binaryChunk.encode();
  }

  //  @Test
  //  void testHelloWorldLuac54OutDecode() throws IOException {
  //    var binaryChunk = new BinaryChunk();
  //    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(helloWorldLuac54Out);
  //    DecodeInputStream decodeInputStream = new DecodeInputStream(byteArrayInputStream);
  //    binaryChunk.decode(decodeInputStream);
  //  }
}
