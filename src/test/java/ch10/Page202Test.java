package ch10;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants.LuaResource;
import constant.ResourceContentConstants.ch10;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class Page202Test {

  static String run(LuaResource luaResource) {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    JavaFunctionExample javaFunctionExample = new JavaFunctionExample(byteArrayOutputStream);

    LuaVM luaVM = LuaVM.create(1, new Prototype());
    luaVM.register(LuaValue.of("print"), javaFunctionExample::print);
    luaVM.load(luaResource.getLuacOut(), luaResource.getLuaFilePath(), "b");
    luaVM.call(0, 0);

    // 返回 stdout
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }
  @Test
  void page_185_upvalue() {
    run(ch10.page_185_upvalue);
  }

  @Test
  void page_186_upvalue_nested() {
    run(ch10.page_186_upvalue_nested);
  }

  @Test
  void page_188_global_variable() {
    run(ch10.page_188_global_variable);
  }

  @Test
  void page_195_GETUPVAL() {
    run(ch10.page_195_GETUPVAL);
  }

  @Test
  void page_197_SETUPVAL() {
    run(ch10.page_197_SETUPVAL);
  }

  @Test
  void page_198_GETTABUP() {
    run(ch10.page_198_GETTABUP);
  }

  @Test
  void page_199_SETTABUP() {
    run(ch10.page_199_SETTABUP);
  }

  @Test
  void page_200_JMP() {
    run(ch10.page_200_JMP);
  }

  @Test
  void page_202_test() {
    run(ch10.page_202_test);
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
