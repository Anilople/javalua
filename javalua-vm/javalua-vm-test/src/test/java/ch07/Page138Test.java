package ch07;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Opcode;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;
import constant.ResourceContentConstants.ch07;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class Page138Test {

  @Test
  void ch07TestLuac53OutEval() {
    BinaryChunk binaryChunk = BinaryChunk.of(ch07.test.getLuacOut());
    var mainFunc = binaryChunk.getMainFunc();
    // stack的size大一些
    LuaVM luaVM = LuaVM.newLuaVM(mainFunc.getMaxStackSize() * 2 + 8, mainFunc);
    // 提前配置好top
    var nRegisters = mainFunc.getMaxStackSize();
    luaVM.setTop(nRegisters);

    // 指令数量
    assertEquals(14, mainFunc.getCode().getCode().length);
    for (Instruction instruction = luaVM.fetch();
        !instruction.getOpcode().equals(Opcode.RETURN);
        instruction = luaVM.fetch()) {
      LuaVM.printLuaVM(luaVM);
      LuaVM.applyPrint(instruction, luaVM);
    }
    // pc:13	stack:[Table:3 {"foo" = "Bar",1 = "a",2 = "B",3 = "c"}]["cBaBar3"]["B"]["a"]["Bar"][3]
    assertEquals(LuaString.newLuaString("cBaBar3"), luaVM.toLuaString(2));
    assertEquals(LuaString.newLuaString("B"), luaVM.toLuaString(3));
    assertEquals(LuaString.newLuaString("a"), luaVM.toLuaString(4));
    assertEquals(LuaString.newLuaString("Bar"), luaVM.toLuaString(5));
    assertEquals(LuaInteger.newLuaInteger(3), luaVM.toLuaInteger(6));
  }
}
