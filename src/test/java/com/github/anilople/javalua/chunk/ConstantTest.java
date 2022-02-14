package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.chunk.Constant.Tag;
import com.github.anilople.javalua.io.DecodeInputStream;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class ConstantTest {

  @Test
  void decodeLuaBooleanTrue() {
    Constant constant = new Constant();
    DecodeInputStream decodeInputStream = new DecodeInputStream(
        new byte[]{
            Tag.BOOLEAN,
            1
        }
    );
    constant.decode(decodeInputStream);
    assertThrows(IllegalStateException.class, decodeInputStream::readByte);

    assertTrue(constant.isLuaBoolean());
    assertTrue(constant.getLuaBooleanInJava());
  }
}