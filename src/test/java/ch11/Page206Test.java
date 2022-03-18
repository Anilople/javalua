package ch11;

import org.junit.jupiter.api.Test;

import constant.ResourceContentConstants.ch11;
import static util.LuaVMUtils.run;

/**
 * @author wxq
 */
public class Page206Test {

  @Test
  void page_206_debug_setmetatable() {
    run(ch11.page_206_debug_setmetatable);
  }

  @Test
  void page_206_getmetatable() {
    run(ch11.page_206_getmetatable);
  }
}
