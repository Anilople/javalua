package ch08;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants.ch08;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page165Test {

  @Test
  void maxCase1() {
    var callFrame = LuaVM.evalAndPrint(ch08.max_case1.getLuacOut());
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    Assertions.assertEquals(LuaInteger.newLuaInteger(64), returnedLuaValue);
  }

  @Test
  void maxCase2() {
    var callFrame = LuaVM.evalAndPrint(ch08.max_case2.getLuacOut());
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    Assertions.assertEquals(LuaInteger.newLuaInteger(3), returnedLuaValue);
  }

  @Test
  void maxWithVarargCase1() {
    var callFrame = LuaVM.evalAndPrint(ch08.max_with_vararg_case1.getLuacOut());
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    Assertions.assertEquals(LuaInteger.newLuaInteger(99), returnedLuaValue);
  }

  @Test
  void maxWithVarargCase2() {
    LuaVM.evalAndPrint(ch08.max_with_vararg_case1.getLuacOut());
  }

  @Test
  void test() {
    LuaVM.evalAndPrint(ch08.test.getLuacOut());
  }
}
