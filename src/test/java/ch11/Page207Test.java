package ch11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.LuaVMUtils.run;

import constant.ResourceContentConstants.ch11;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page207Test {
  @Test
  void page_207_metatable_add() {
    String stdout = run(ch11.page_207_metatable_add);
    System.out.println(stdout);
    assertEquals("4\t7", stdout);
  }
}
