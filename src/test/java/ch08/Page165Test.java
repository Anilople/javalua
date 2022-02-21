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
  void ch08MaxCase1() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxCase1);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(64), returnedLuaValue);
  }

  @Test
  void ch08MaxCase2() {
    var callFrame = LuaVM.evalAndPrint(ch08.maxCase2);
    LuaValue returnedLuaValue = callFrame.pop();
    assertTrue(returnedLuaValue instanceof LuaInteger);
    assertEquals(LuaValue.of(3), returnedLuaValue);
  }

  @Test
  void ch08Test() {
    var callFrame = LuaVM.evalAndPrint(ch08.test);
  }
}
