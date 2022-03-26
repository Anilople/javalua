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
  }

  @Test
  void ipairs_case1() {
    String stdout = LuaVMUtils.run(ch12.ipairs_case1);
    System.out.println(stdout);
  }

  @Test
  void ipairs_empty_array() {
    String stdout = LuaVMUtils.run(ch12.ipairs_empty_array);
    System.out.println(stdout);
    assertEquals("", stdout);
  }
}
