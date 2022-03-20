package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaVM;
import java.util.function.Function;

/**
 * page 168
 *
 * 和书里的 Go function 对应
 *
 * 输入{@link LuaState}，返回一个整数，表示这个 java function有多少个返回值
 *
 * @author wxq
 */
public interface JavaFunction extends Function<LuaState, Integer> {

  /**
   * 把当前的 java function 注册到 lua vm中
   */
  void registerTo(LuaVM luaVM);
}
