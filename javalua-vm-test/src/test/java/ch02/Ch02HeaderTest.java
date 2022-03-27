package ch02;

import com.github.anilople.javalua.chunk.Header;
import com.github.anilople.javalua.util.ByteUtils;
import constant.ResourceContentConstants;
import constant.ResourceContentConstants.ch02;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.anilople.javalua.chunk.Header.LUAC_DATA;
import static com.github.anilople.javalua.chunk.Header.LUA_SIGNATURE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class Ch02HeaderTest {

  @Test
  void helloWorldLuac53OutSignature() {
    for (int i = 0; i < 4; i++) {
      assertEquals(Header.INSTANCE.getLuaSignature()[i], ch02.hello_world.getLuacOut()[i]);
    }
    assertArrayEquals(
        "Lua".getBytes(StandardCharsets.UTF_8),
        new byte[] {
            ResourceContentConstants.ch02.hello_world.getLuacOut()[1],
            ResourceContentConstants.ch02.hello_world.getLuacOut()[2],
            ResourceContentConstants.ch02.hello_world.getLuacOut()[3]
        });
  }

  @Test
  void helloWorldLuac53OutHeaderDecode() throws IOException {
    var expectedHeader = new Header();
    var bytes =
        Arrays.copyOfRange(ResourceContentConstants.ch02.hello_world.getLuacOut(), 0, Header.SIZE);
    var header = ByteUtils.decode(bytes, Header.class);

    Assertions.assertArrayEquals(expectedHeader.encode(), header.encode());
  }


  @Test
  void helloWorldLuac53OutHeaderEncode() {
    byte[] expectedHeaderByteArray =
        Arrays.copyOfRange(ResourceContentConstants.ch02.hello_world.getLuacOut(), 0, Header.SIZE);

    byte[] actualHeaderByteArray = new Header().encode();

    int pos = 0;
    // LUA_SIGNATURE
    Assertions.assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, Header.LUA_SIGNATURE.length),
        Arrays.copyOfRange(actualHeaderByteArray, pos, Header.LUA_SIGNATURE.length),
        "LUA_SIGNATURE");
    pos += Header.LUA_SIGNATURE.length;

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
    Assertions.assertArrayEquals(
        Arrays.copyOfRange(expectedHeaderByteArray, pos, Header.LUAC_DATA.length),
        Arrays.copyOfRange(actualHeaderByteArray, pos, Header.LUAC_DATA.length),
        "LUAC_DATA");
    pos += Header.LUAC_DATA.length;

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

    Assertions.assertEquals(Header.SIZE, pos);
  }

}
