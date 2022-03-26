package ch12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import constant.ResourceContentConstants.ch12;
import org.junit.jupiter.api.Test;
import util.LuaVMUtils;

/**
 * @author wxq
 */
class SelfDefineTest {

  @Test
  void ipairs_array_1_element() {
    String stdout = LuaVMUtils.run(ch12.ipairs_array_1_element);
    System.out.println(stdout);
    assertEquals("1\t999\n", stdout);
  }

  @Test
  void ipairs_case1() {
    String stdout = LuaVMUtils.run(ch12.ipairs_case1);
    System.out.println(stdout);
    assertEquals("1\t10\n" + "2\t20\n" + "3\t30\n", stdout);
  }

  @Test
  void ipairs_empty_array() {
    String stdout = LuaVMUtils.run(ch12.ipairs_empty_array);
    System.out.println(stdout);
    assertEquals("", stdout);
  }

  @Test
  void page_165_test_simpler_case1() {
    String stdout = LuaVMUtils.run(ch12.page_165_test_simpler_case1);
    System.out.println(stdout);
    assertEquals("", stdout);
  }

  @Test
  void page_165_test_simpler_case2() {
    String stdout = LuaVMUtils.run(ch12.page_165_test_simpler_case2);
    System.out.println(stdout);
    assertEquals("", stdout);
  }

  @Test
  void pairs_array_1_element() {
    String stdout = LuaVMUtils.run(ch12.pairs_array_1_element);
    System.out.println(stdout);
    assertEquals("a\t10\n", stdout);
  }

  @Test
  void pairs_array_3_element() {
    String stdout = LuaVMUtils.run(ch12.pairs_array_3_element);
    System.out.println(stdout);
    assertEquals("a\t10\n" + "b\t20\n" + "c\t30\n", stdout);
  }

  @Test
  void pairs_empty_array() {
    String stdout = LuaVMUtils.run(ch12.pairs_empty_array);
    System.out.println(stdout);
    assertEquals("", stdout);
  }
}
