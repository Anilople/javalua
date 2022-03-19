package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaVM;
import java.util.function.Function;

/**
 * page 168
 *
 * 和书里的 Go function 对应
 *
 * @author wxq
 */
public interface JavaFunction extends Function<LuaState, Integer> {
  void registerTo(LuaVM luaVM);
}
