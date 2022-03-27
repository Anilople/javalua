package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.DecodeInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class BinaryChunkTest {

  @Test
  void testBinaryChunkEncodeCase1() {
    var binaryChunk = new BinaryChunk();
    binaryChunk.header = Header.INSTANCE;
    binaryChunk.sizeUpvalues = 2;
    binaryChunk.mainFunc = new Prototype();

    binaryChunk.encode();
  }

  @Test
  void decodeSelfEquals() throws IOException {
    BinaryChunk binaryChunk = new BinaryChunk();
    BinaryChunk binaryChunkNew = new BinaryChunk();
    binaryChunkNew.decode(new DecodeInputStream(binaryChunk.encode()));

    assertEquals(binaryChunk.header, binaryChunkNew.header);
    assertEquals(binaryChunk.sizeUpvalues, binaryChunkNew.sizeUpvalues);
    assertEquals(binaryChunk.mainFunc, binaryChunkNew.mainFunc);
  }


}
