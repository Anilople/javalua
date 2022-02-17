package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.state.LuaState;

/**
 * @author wxq
 */
public interface LuaVM extends LuaState {

  static LuaVM create(int stackSize, Prototype prototype) {
    return new DefaultLuaVMImpl(stackSize, prototype);
  }

  static LuaVM of(byte[] binaryChunk) {
    var prototype = BinaryChunk.getPrototype(binaryChunk);
    return create(prototype.getMaxStackSize(), prototype);
  }

  static void printLuaVM(LuaVM luaVM) {
    System.out.print("pc:" + luaVM.pc());
    System.out.print("\tstack:");
    LuaState.printStack(luaVM);
  }

  static void applyPrint(Instruction instruction, LuaVM luaVM) {
    instruction.applyTo(luaVM);
    System.out.println("after run " + instruction + " change to");
    printLuaVM(luaVM);
    System.out.println();
  }

  static void fetchApplyPrint(LuaVM luaVM) {
    printLuaVM(luaVM);
    var instruction = luaVM.fetch();
    applyPrint(instruction, luaVM);
  }

  static void eval(LuaVM luaVM) {
    for (var instruction = luaVM.fetch(); ; instruction = luaVM.fetch()) {
      if (Opcode.RETURN.equals(instruction.getOpcode())) {
        break;
      }
      instruction.applyTo(luaVM);
    }
  }
}
