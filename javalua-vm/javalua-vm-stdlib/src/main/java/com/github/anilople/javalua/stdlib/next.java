package com.github.anilople.javalua.stdlib;

import com.github.anilople.javalua.state.LuaState;

/**
 * page 229
 *
 * @author wxq
 */
public class next extends AbstractJavaFunction {

  private static final next INSTANCE = new next();

  public static next getInstance() {
    return INSTANCE;
  }

  @Override
  public Integer apply(LuaState luaState) {
    // pad nil
    // i.e next(t) => next(t, nil)
    luaState.setTop(2);
    boolean hasNext = luaState.next(1);
    if (hasNext) {
      return 2;
    } else {
      luaState.pushLuaNil();
      return 1;
    }
  }
}
