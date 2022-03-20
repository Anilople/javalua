package ch11;

import static util.LuaVMUtils.run;

import constant.ResourceContentConstants.ch11;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class Page206Test {

  @Test
  @Disabled("debug.setmetatable 还没有支持")
  void page_206_debug_setmetatable() {
    run(ch11.page_206_debug_setmetatable);
  }

  @Test
  void page_206_getmetatable() {
    String stdout = run(ch11.page_206_getmetatable);
    System.out.println(stdout);
  }
}
