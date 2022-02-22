package ch08;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants.ch08;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page165Test {

  @Test
  void maxCase1() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxCase1);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(64), returnedLuaValue);
  }

  @Test
  void maxCase2() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxCase2);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(3), returnedLuaValue);
  }

  @Test
  void maxWithVarargCase1() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxWithVarargCase1);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(99), returnedLuaValue);
  }

  @Test
  void maxWithVarargCase2() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxWithVarargCase2);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(199), returnedLuaValue);
  }

  @Test
  void maxWithVarargCase3() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxWithVarargCase3);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(128), returnedLuaValue);
  }

  @Test
  void test() {
    var callFrame = LuaVM.evalAndPrint(ch08.test);
    System.out.println(callFrame);
  }


}
