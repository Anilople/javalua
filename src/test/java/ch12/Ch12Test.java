package ch12;

import constant.ResourceContentConstants.ch12;
import org.junit.jupiter.api.Test;
import util.LuaVMUtils;

/**
 * @author wxq
 */
public class Ch12Test {

  @Test
  void page_224_array_iterate() {
    String stdout = LuaVMUtils.run(ch12.page_224_array_iterate);
    System.out.println(stdout);
  }

  @Test
  void page_224_for_in() {
    LuaVMUtils.run(ch12.page_224_for_in);
  }

  @Test
  void page_225_for_in_with_next() {
    LuaVMUtils.run(ch12.page_225_for_in_with_next);
  }

  @Test
  void page_226_for_in_with_next_simpler() {
    LuaVMUtils.run(ch12.page_226_for_in_with_next_simpler);
  }

  @Test
  void page_234_test() {
    LuaVMUtils.run(ch12.page_234_test);
  }
}
