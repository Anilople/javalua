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

  @Test
  @DisplayName("从数组中获取元素")
  void get_from_array() {
    String stdout = LuaVMUtils.run(ch11.get_from_array);
    assertEquals("222", stdout);
  }

  @Test
  @DisplayName("从数组中获取元素 带vararg")
  void get_from_array_vararg() {
    String stdout = LuaVMUtils.run(ch11.get_from_array_vararg);
    assertEquals("666666", stdout);
  }

  @Test
  @DisplayName("__len 固定长度")
  void __len_fixed() {
    String stdout = LuaVMUtils.run(ch11.__len_fixed);
    assertEquals("999", stdout);
  }

  @Test
  @DisplayName("不执行元方法__eq")
  void __eq_not_call() {
    String stdout = LuaVMUtils.run(ch11.__eq_not_call);
    assertEquals("truetrue", stdout);
  }

  @Test
  void __eq_always_true() {
    String stdout = LuaVMUtils.run(ch11.__eq_always_true);
    assertEquals("falsetrue", stdout);
  }
}
