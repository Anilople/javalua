package ch11;

import static org.junit.jupiter.api.Assertions.*;

import constant.ResourceContentConstants.ch11;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.LuaVMUtils;

/**
 * @author wxq
 */
class SelfDefineTest {

  @Test
  void getmetatable_nil() {
    String stdout = LuaVMUtils.run(ch11.getmetatable_nil);
    assertEquals("nil", stdout);
  }

  @Test
  void getmetatable_string() {
    // TODO stdout like table: 0000000000eb9f90
    String stdout = LuaVMUtils.run(ch11.getmetatable_string);
    System.out.println(stdout);
  }

  @Test
  void setmetatable_case1() {
    String stdout = LuaVMUtils.run(ch11.setmetatable_case1);
    assertEquals("999", stdout);
  }

  @Test
  @DisplayName("通过减法检查函数入参的顺序")
  void sub() {
    String stdout = LuaVMUtils.run(ch11.sub);
    assertEquals("111", stdout);
  }
}
