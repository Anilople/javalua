package ch09;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.stdlib.print;
import constant.ResourceContentConstants.ch02;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page180Test {

  @Test
  void testJavaFunctionPrint() {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    LuaVM luaVM = LuaVM.of(ch02.hello_world.getLuacOut());
    new print(new PrintStream(byteArrayOutputStream)).registerTo(luaVM);
    luaVM.load(ch02.hello_world.getLuacOut(), "hello_world.lua", "b");
    luaVM.call(0, 0);

    // 检查是否真的输出了 "Hello, World!"
    String stringPrinted = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    assertEquals("Hello, World!\n", stringPrinted);
  }
}
