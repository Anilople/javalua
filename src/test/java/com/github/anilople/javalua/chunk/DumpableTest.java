package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class DumpableTest {

  @Test
  void dumpCase1() {
    var person = new Person();
    person.b = 0x0;
    person.array = new byte[] {0x1, 0x2};
    assertArrayEquals(new byte[] {0x0, 0x1, 0x2}, person.dump());
  }

  static class Person implements Dumpable {

    byte b;
    byte[] array;
  }
}
