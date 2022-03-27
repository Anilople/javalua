package ch13;

import com.github.anilople.javalua.exception.LuaErrorRuntimeException;
import constant.ResourceContentConstants.ch13;
import org.junit.jupiter.api.Test;
import util.LuaVMUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author wxq
 */
class SelfDefineTest {
  @Test
  void error_case1() {
    assertThrows(RuntimeException.class, () -> LuaVMUtils.run(ch13.error_case1));
  }

  @Test
  void pcall_error() {
    String stdout = LuaVMUtils.run(ch13.pcall_error);
    System.out.println(stdout);
    assertEquals("false\n", stdout);
  }

  @Test
  void pcall_print() {
    String stdout = LuaVMUtils.run(ch13.pcall_print);
    System.out.println(stdout);
    assertEquals(
        "1\t2\t3\n"
        + "true\n",
        stdout
    );
  }
}
