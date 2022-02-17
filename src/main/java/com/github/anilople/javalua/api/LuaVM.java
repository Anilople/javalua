package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.state.LuaState;

/**
 * @author wxq
 */
public interface LuaVM extends LuaState {

  static LuaVM create(int stackSize, Prototype prototype) {
    return new DefaultLuaVMImpl(stackSize, prototype);
  }
}
