package ch09;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
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
    JavaFunctionExample javaFunctionExample = new JavaFunctionExample(byteArrayOutputStream);

    LuaVM luaVM = LuaVM.of(ch02.hello_world.getLuacOut());
    luaVM.register(LuaValue.of("print"), javaFunctionExample::print);
    luaVM.load(ch02.hello_world.getLuacOut(), "hello_world.lua", "b");
    luaVM.call(0, 0);

    // 检查是否真的输出了 "Hello, World!"
    String stringPrinted = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    assertEquals("Hello, World!", stringPrinted);
  }

  static class JavaFunctionExample {
    private final PrintStream printStream;

    JavaFunctionExample(ByteArrayOutputStream byteArrayOutputStream) {
      this.printStream = new PrintStream(byteArrayOutputStream);
    }

    /**
     * page 179
     * 第一个Java函数
     * 对应Lua里的print函数
     */
    int print(LuaState luaState) {
      int nArgs = luaState.getTop();
      for (int index = 1; index <= nArgs; index++) {
        LuaValue luaValue = luaState.toLuaValue(index);
        if (luaValue instanceof LuaString) {
          LuaString luaString = (LuaString) luaValue;
          printStream.print(luaString.getValue());
        } else {
          throw new UnsupportedOperationException("cannot print " + luaValue);
        }
        if (index < nArgs) {
          printStream.print("\t");
        }
      }
      return 0;
    }
  }
}
