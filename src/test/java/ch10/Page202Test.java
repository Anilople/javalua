package ch10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.stdlib.Print;
import com.github.anilople.javalua.chunk.Prototype;
import constant.ResourceContentConstants.LuaResource;
import constant.ResourceContentConstants.ch10;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page202Test {

  static String run(LuaResource luaResource) {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    LuaVM luaVM = LuaVM.create(1, new Prototype());
    Print.registerTo(luaVM, new PrintStream(byteArrayOutputStream));
    luaVM.load(luaResource.getLuacOut(), luaResource.getLuaFilePath(), "b");
    luaVM.call(0, 0);

    // 返回 stdout
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }

  @Test
  void function_call_nested_case1() {
    String stdout = run(ch10.function_call_nested_case1);
    assertEquals("889", stdout);
  }

  @Test
  void upvalue_add_case1() {
    String stdout = run(ch10.upvalue_add_case1);
    assertEquals("99", stdout);
  }

  @Test
  void upvalue_add_case2() {
    String stdout = run(ch10.upvalue_add_case2);
    assertEquals("100", stdout);
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
    String stdout = run(ch10.page_202_test);
    System.out.println(stdout);
    assertEquals("12132", stdout);
  }

  @Test
  void page_202_test_simpler_case1() {
    String stdout = run(ch10.page_202_test_simpler_case1);
    System.out.println(stdout);
    assertEquals("1", stdout);
  }
}
