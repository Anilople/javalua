package ch07;

import static com.github.anilople.javalua.api.LuaVM.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page138Test {

  @Test
  void ch07TestLuac53OutEval() {
    BinaryChunk binaryChunk = BinaryChunk.of(ResourceContentConstants.ch07.testLuac53Out);
    var mainFunc = binaryChunk.getMainFunc();
    // stack的size大一些
    LuaVM luaVM = LuaVM.create(mainFunc.getMaxStackSize() * 2 + 8, mainFunc);
    // 提前配置好top
    var nRegisters = mainFunc.getMaxStackSize();
    luaVM.setTop(nRegisters);

    // 指令数量
    assertEquals(14, mainFunc.getCode().getInstructions().length);
    for (Instruction instruction = luaVM.fetch();
        !instruction.getOpcode().equals(Opcode.RETURN);
        instruction = luaVM.fetch()) {
      printLuaVM(luaVM);
      applyPrint(instruction, luaVM);
    }
    // pc:13	stack:[Table:3 {"foo" = "Bar",1 = "a",2 = "B",3 = "c"}]["cBaBar3"]["B"]["a"]["Bar"][3]
    assertEquals(LuaValue.of("cBaBar3"), luaVM.toLuaString(2));
    assertEquals(LuaValue.of("B"), luaVM.toLuaString(3));
    assertEquals(LuaValue.of("a"), luaVM.toLuaString(4));
    assertEquals(LuaValue.of("Bar"), luaVM.toLuaString(5));
    assertEquals(LuaValue.of(3), luaVM.toLuaInteger(6));
  }
}
