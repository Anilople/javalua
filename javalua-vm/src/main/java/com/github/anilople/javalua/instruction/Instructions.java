package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * @author wxq
 */
public class Instructions {
  public static void move(Instruction instruction, LuaVM luaVM) {
    instruction.getOperand().A();
    //    luaVM.copy();
  }
}
