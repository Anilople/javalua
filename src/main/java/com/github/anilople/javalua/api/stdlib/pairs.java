package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.state.LuaState;

/**
 * page 232
 *
 * 相当于
 * <pre>
 *   {@code
 *   function pairs(t)
 *      return next, t, nil
 *   end
 *   }
 * </pre>
 *
 * @author wxq
 */
public class pairs extends AbstractJavaFunction {

  private static final pairs INSTANCE = new pairs();

  public static pairs getInstance() {
    return INSTANCE;
  }

  @Override
  public Integer apply(LuaState luaState) {
    luaState.pushJavaFunction(next.getInstance());
    luaState.pushValue(1);
    luaState.pushLuaNil();
    return 3;
  }
}
