package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.state.DefaultLuaStateImpl;
import com.github.anilople.javalua.state.LuaValue;

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
