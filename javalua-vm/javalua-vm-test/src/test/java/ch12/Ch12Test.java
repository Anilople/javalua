package ch12;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals("1\t10\n" + "2\t20\n" + "3\t30\n", stdout);
  }

  @Test
  void page_224_for_in() {
    String stdout = LuaVMUtils.run(ch12.page_224_for_in);
    System.out.println(stdout);
    assertEquals("1\t10\n" + "2\t20\n" + "3\t30\n", stdout);
  }

  @Test
  void page_225_for_in_with_next() {
    String stdout = LuaVMUtils.run(ch12.page_225_for_in_with_next);
    System.out.println(stdout);
    assertEquals("a\t10\n" + "b\t20\n" + "c\t30\n", stdout);
  }

  @Test
  void page_226_for_in_with_next_simpler() {
    String stdout = LuaVMUtils.run(ch12.page_226_for_in_with_next_simpler);
    assertEquals("a\t10\n" + "b\t20\n" + "c\t30\n", stdout);
  }

  @Test
  void page_234_test() {
    String stdout = LuaVMUtils.run(ch12.page_234_test);
    assertEquals("a\t1\n" + "b\t2\n" + "c\t3\n" + "1\ta\n" + "2\tb\n" + "3\tc\n", stdout);
  }
}
