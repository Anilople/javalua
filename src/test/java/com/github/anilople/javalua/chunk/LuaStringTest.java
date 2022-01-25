package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaStringTest {

  @Test
  void testNull() throws IOException {
    assertArrayEquals(new byte[] {0}, LuaString.NULL.encode());
    LuaString luaString = new LuaString();
    luaString.decode(new DecodeInputStream(new byte[] {0}));
    assertEquals(0, luaString.first);

    assertEquals(LuaString.NULL, luaString);
  }

  @Test
  void testNullEqualsSelf() throws IOException {
    LuaString luaString = new LuaString();
    luaString.decode(new DecodeInputStream(LuaString.NULL.encode()));
    assertEquals(LuaString.NULL, luaString);
  }

  @Test
  void testSmall() throws IOException {
    LuaString expected = new LuaString();
    expected.first = 4;
    expected.bytes = "lua".getBytes(StandardCharsets.UTF_8);

    LuaString actual = new LuaString();
    actual.decode(new DecodeInputStream(new byte[] {4, 'l', 'u', 'a'}));
    assertEquals(expected, actual);
  }

  @Test
  void testSmallStringSize253() throws IOException {
    LuaString expected = new LuaString();
    expected.first = (byte) 0xFE;
    expected.bytes = "k".repeat(253).getBytes(StandardCharsets.UTF_8);

    LuaString actual = new LuaString();
    actual.decode(
        new DecodeInputStream(
            ArrayUtils.concat((byte) 0xFE, "k".repeat(253).getBytes(StandardCharsets.UTF_8))));
    assertEquals(expected, actual);
  }

  @Test
  void testBigStringSize254() throws IOException {
    LuaString expected = new LuaString();
    expected.first = (byte) 0xFF;
    assertEquals(-1, expected.first);

    expected.bytes = "k".repeat(254).getBytes(StandardCharsets.UTF_8);

    LuaString actual = new LuaString();
    actual.decode(
        new DecodeInputStream(
            ArrayUtils.concat(
                (byte) 0xFF,
                ByteUtils.encodeLong(254 + 1),
                "k".repeat(254).getBytes(StandardCharsets.UTF_8))));
    assertEquals(expected, actual);
  }

  @Test
  void testBigStringSize255() throws IOException {
    LuaString expected = new LuaString();
    expected.first = (byte) 0xFF;
    assertEquals(-1, expected.first);

    expected.bytes = "k".repeat(255).getBytes(StandardCharsets.UTF_8);

    LuaString actual = new LuaString();
    actual.decode(
        new DecodeInputStream(
            ArrayUtils.concat(
                (byte) 0xFF,
                ByteUtils.encodeLong(255 + 1),
                "k".repeat(255).getBytes(StandardCharsets.UTF_8))));
    assertEquals(expected, actual);
  }

  @Test
  void testDecodeCase1() throws IOException {
    LuaString luaString = new LuaString();
    luaString.decode(
        new DecodeInputStream(
            ArrayUtils.concat((byte) 0x11, "@hello_world.lua".getBytes(StandardCharsets.UTF_8))));

    assertEquals((byte) 0x11, luaString.first);
    assertEquals(16, luaString.bytes.length);
    assertEquals("@hello_world.lua", new String(luaString.bytes));
  }
}
