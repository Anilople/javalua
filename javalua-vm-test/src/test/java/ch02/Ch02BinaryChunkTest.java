package ch02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.io.DecodeInputStream;
import constant.ResourceContentConstants;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Ch02BinaryChunkTest {
  @Test
  void testHelloWorldLuac53OutSize() {
    assertEquals(151, ResourceContentConstants.ch02.hello_world.getLuacOut().length);
  }

  @Test
  void testHelloWorldLuac54OutSize() {
    assertEquals(117, ResourceContentConstants.ch02.helloWorldLuac54Out.length);
  }

  @Test
  void helloWorldLuac53OutDecode() throws IOException {
    var binaryChunk = new BinaryChunk();
    DecodeInputStream inputStream =
        new DecodeInputStream(ResourceContentConstants.ch02.hello_world.getLuacOut());
    binaryChunk.decode(inputStream);

    // 检测是否读取完成
    assertThrows(IllegalStateException.class, inputStream::readByte);

    Instruction[] instructions = binaryChunk.getMainFunc().getCode().getInstructions();
    for (Instruction instruction : instructions) {
      System.out.println(instruction.getOpcode());
    }
    Assertions.assertEquals("GETTABUP", instructions[0].getOpcode().getName());
    Assertions.assertEquals("LOADK", instructions[1].getOpcode().getName());
    Assertions.assertEquals("CALL", instructions[2].getOpcode().getName());
    Assertions.assertEquals("RETURN", instructions[3].getOpcode().getName());
  }
}
