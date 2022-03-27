package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.io.DecodeInputStream;
import constant.ResourceContentConstants;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class BinaryChunkTest {

  BinaryChunkTest() throws IOException {}

  @Test
  void testHelloWorldLuac53OutSize() {
    assertEquals(151, ResourceContentConstants.ch02.hello_world.getLuacOut().length);
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
  void decodeSelfEquals() throws IOException {
    BinaryChunk binaryChunk = new BinaryChunk();
    BinaryChunk binaryChunkNew = new BinaryChunk();
    binaryChunkNew.decode(new DecodeInputStream(binaryChunk.encode()));

    assertEquals(binaryChunk.header, binaryChunkNew.header);
    assertEquals(binaryChunk.sizeUpvalues, binaryChunkNew.sizeUpvalues);
    assertEquals(binaryChunk.mainFunc, binaryChunkNew.mainFunc);
  }

  @Test
  void helloWorldLuac53OutDecode() throws IOException {
    var binaryChunk = new BinaryChunk();
    DecodeInputStream inputStream =
        new DecodeInputStream(ResourceContentConstants.ch02.hello_world.getLuacOut());
    binaryChunk.decode(inputStream);

    // 检测是否读取完成
    assertThrows(IllegalStateException.class, inputStream::readByte);

    Instruction[] instructions = binaryChunk.mainFunc.code.getInstructions();
    for (Instruction instruction : instructions) {
      System.out.println(instruction.getOpcode());
    }
    assertEquals("GETTABUP", instructions[0].getOpcode().getName());
    assertEquals("LOADK", instructions[1].getOpcode().getName());
    assertEquals("CALL", instructions[2].getOpcode().getName());
    assertEquals("RETURN", instructions[3].getOpcode().getName());
  }
}
