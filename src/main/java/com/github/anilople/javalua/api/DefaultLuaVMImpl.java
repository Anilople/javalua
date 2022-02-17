package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.state.DefaultLuaStateImpl;

/**
 * page 93
 *
 * @author wxq
 */
class DefaultLuaVMImpl extends DefaultLuaStateImpl implements LuaVM {

  DefaultLuaVMImpl(int stackSize, Prototype prototype) {
    super(stackSize, prototype);
  }
}
