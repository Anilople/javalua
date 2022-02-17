package com.github.anilople.javalua.chunk;

import static com.github.anilople.javalua.chunk.Header.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import constant.ResourceContentConstants;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class HeaderTest {

  @Test
  void helloWorldLuac53OutSignature() {
    for (int i = 0; i < 4; i++) {
      assertEquals(
          Header.INSTANCE.luaSignature[i], ResourceContentConstants.ch02.helloWorldLuac53Out[i]);
    }
    assertArrayEquals(
        "Lua".getBytes(StandardCharsets.UTF_8),
        new byte[] {
          ResourceContentConstants.ch02.helloWorldLuac53Out[1],
          ResourceContentConstants.ch02.helloWorldLuac53Out[2],
          ResourceContentConstants.ch02.helloWorldLuac53Out[3]
        });
  }

  @Test
  void testHeaderDump() {
    byte[] bytes = Header.INSTANCE.encode();
    assertEquals(33, bytes.length);
  }

  @Test
  void helloWorldLuac53OutHeaderEncode() {
    byte[] expectedHeaderByteArray =
        Arrays.copyOfRange(ResourceContentConstants.ch02.helloWorldLuac53Out, 0, Header.SIZE);

    byte[] actualHeaderByteArray = new Header().encode();

    int pos = 0;
    // LUA_SIGNATURE
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, LUA_SIGNATURE.length),
        Arrays.copyOfRange(actualHeaderByteArray, pos, LUA_SIGNATURE.length),
        "LUA_SIGNATURE");
    pos += LUA_SIGNATURE.length;

    // LUAC_VERSION
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "LUAC_VERSION");
    pos++;

    // until LUAC_FORMAT
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "LUAC_FORMAT");
    pos++;

    // until LUAC_DATA
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, LUAC_DATA.length),
        Arrays.copyOfRange(actualHeaderByteArray, pos, LUAC_DATA.length),
        "LUAC_DATA");
    pos += LUAC_DATA.length;

    // until sizeof(int)
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "sizeof(int)");
    pos++;

    // until sizeof(size_t)
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "sizeof(size_t)");
    pos++;

    // until SIZE_OF_INSTRUCTION
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "SIZE_OF_INSTRUCTION");
    pos++;

    // until SIZE_OF_LUA_INTEGER
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "SIZE_OF_LUA_INTEGER");
    pos++;

    // until SIZE_OF_LUA_NUMBER
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 1),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 1),
        "SIZE_OF_LUA_NUMBER");
    pos++;

    // until LUAC_INT
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 8),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 8),
        "LUAC_INT");
    pos += 8;

    // until LUAC_NUM
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, pos + 8),
        Arrays.copyOfRange(actualHeaderByteArray, pos, pos + 8),
        "LUAC_NUM");
    pos += 8;

    assertEquals(Header.SIZE, pos);
  }

  @Test
  void testHeaderUndumpSelf() throws IOException {
    var bytes = Header.INSTANCE.encode();
    var actualHeader = ByteUtils.decode(bytes, Header.class);
    assertArrayEquals(bytes, actualHeader.encode());
  }

  @Test
  void helloWorldLuac53OutHeaderDecode() throws IOException {
    var expectedHeader = new Header();
    var bytes =
        Arrays.copyOfRange(ResourceContentConstants.ch02.helloWorldLuac53Out, 0, Header.SIZE);
    var header = ByteUtils.decode(bytes, Header.class);

    assertArrayEquals(expectedHeader.encode(), header.encode());
  }
}
