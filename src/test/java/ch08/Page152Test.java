package ch08;

import com.github.anilople.javalua.api.LuaVM;
import constant.ResourceContentConstants.ch08;

/**
 * @author wxq
 */
class Page152Test {

  //  @Test
  void testClosure() {
    LuaVM luaVM = LuaVM.of(ch08.closure);
    LuaVM.eval(luaVM);
  }
}
