package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class PrototypeTest {

  @Test
  void decodeSelfEquals() throws IOException {
    Prototype expected = new Prototype();
    Prototype actual = new Prototype();
    actual.decode(new DecodeInputStream(expected.encode()));

    assertEquals(expected.source, actual.source);
    assertEquals(expected.lineDefined, actual.lineDefined);
    assertEquals(expected.lastLineDefined, actual.lastLineDefined);
    assertEquals(expected.numParams, actual.numParams);
    assertEquals(expected.isVararg, actual.isVararg);
    assertEquals(expected.maxStackSize, actual.maxStackSize);
    assertEquals(expected.code, actual.code);
    assertEquals(expected.constants, actual.constants);
    assertArrayEquals(expected.upvalues, actual.upvalues);
    assertArrayEquals(expected.protos, actual.protos);
    assertEquals(expected.debug, actual.debug);

    assertEquals(expected, actual);
  }
}