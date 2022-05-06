package ch09;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.constant.LuaConstants;
import constant.ResourceContentConstants.ch02;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page180Test {

  /**
   * page 180
   *
   * 如果maven的依赖上，没有引入vm的实现，会找不到{@link LuaVM}的实现，进而测试失败.
   */
  @Test
  void testJavaFunctionPrint() {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    var prototype = BinaryChunk.getPrototype(ch02.hello_world.getLuacOut());
    LuaVM luaVM =
        LuaVM.newLuaVM(
            new PrintStream(byteArrayOutputStream),
            prototype.getMaxStackSize() + LuaConstants.LUA_MIN_STACK,
            prototype);
    luaVM.load(ch02.hello_world.getLuacOut(), "hello_world.lua", "b");
    luaVM.call(0, 0);

    // 检查是否真的输出了 "Hello, World!"
    String stringPrinted = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    assertEquals("Hello, World!\n", stringPrinted);
  }
}
