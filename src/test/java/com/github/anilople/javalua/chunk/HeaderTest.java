package com.github.anilople.javalua.chunk;

import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_DATA;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUA_SIGNATURE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.ResourceReadUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class HeaderTest {

  /**
   * little endian
   */
  private final byte[] helloWorldLuac54Out =
      ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");

  public HeaderTest() throws IOException {
  }


  @Test
  void testHelloWorldLuac54OutSignature() {
    for (int i = 0; i < 4; i++) {
      assertEquals(Header.INSTANCE.luaSignature[i], helloWorldLuac54Out[i]);
    }
    assertArrayEquals(
        "Lua".getBytes(StandardCharsets.UTF_8),
        new byte[]{helloWorldLuac54Out[1], helloWorldLuac54Out[2], helloWorldLuac54Out[3]});
  }

  @Test
  void testHeaderDump() {
    byte[] bytes = Header.INSTANCE.encode();
    assertEquals(31, bytes.length);
  }

  @Test
  void testHelloWorldLuac54OutHeaderEncode() {
    byte[] expectedHeaderByteArray = Arrays.copyOfRange(helloWorldLuac54Out, 0, Header.SIZE);

    Header header = new Header();
    header.luacVersion = new Version(5, 4, 4).encode();
    byte[] actualHeaderByteArray = header.encode();

    // LUA_SIGNATURE
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 0, LUA_SIGNATURE.length),
        Arrays.copyOfRange(actualHeaderByteArray, 0, LUA_SIGNATURE.length),
        "LUA_SIGNATURE");

    // LUAC_VERSION
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 4, 5),
        Arrays.copyOfRange(actualHeaderByteArray, 4, 5),
        "LUAC_VERSION");

    // until LUAC_FORMAT
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 5, 6),
        Arrays.copyOfRange(actualHeaderByteArray, 5, 6),
        "LUAC_FORMAT");

    // until LUAC_DATA
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 6, LUAC_DATA.length),
        Arrays.copyOfRange(actualHeaderByteArray, 6, LUAC_DATA.length),
        "LUAC_DATA");

    // until SIZE_OF_INSTRUCTION
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 12, 13),
        Arrays.copyOfRange(actualHeaderByteArray, 12, 13),
        "SIZE_OF_INSTRUCTION");

    // until SIZE_OF_LUA_INTEGER
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 13, 14),
        Arrays.copyOfRange(actualHeaderByteArray, 13, 14),
        "SIZE_OF_LUA_INTEGER");

    // until SIZE_OF_LUA_NUMBER
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 14, 15),
        Arrays.copyOfRange(actualHeaderByteArray, 14, 15),
        "SIZE_OF_LUA_NUMBER");

    // until LUAC_INT
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 15, 23),
        Arrays.copyOfRange(actualHeaderByteArray, 15, 23),
        "LUAC_INT");

    // until LUAC_NUM
    assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, 23, 31),
        Arrays.copyOfRange(actualHeaderByteArray, 23, 31),
        "LUAC_NUM");
  }

  @Test
  void testHeaderUndumpSelf() throws IOException {
    var bytes = Header.INSTANCE.encode();
    var actualHeader = ByteUtils.decode(bytes, Header.class);
    assertEquals(Header.INSTANCE, actualHeader);
  }

  @Test
  void testHelloWorldLuac54OutHeaderDecode() throws IOException {
    var expectedHeader = new Header();
    expectedHeader.luacVersion = new Version(5, 4, 4).encode();

    var bytes = Arrays.copyOfRange(helloWorldLuac54Out, 0, Header.SIZE);
    var header = ByteUtils.decode(bytes, Header.class);

    assertEquals(expectedHeader, header);
  }
}
