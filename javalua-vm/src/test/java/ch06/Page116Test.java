package ch06;

import static com.github.anilople.javalua.api.LuaVM.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants.ch06;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class Page116Test {

  /**
   * 检测指令是否正确
   *
   * page 116
   */
  @Test
  void ch06SumLuac53OutInstructions() {
    BinaryChunk binaryChunk = BinaryChunk.of(ch06.sum.getLuacOut());
    assertEquals(1, binaryChunk.getSizeUpvalues());

    var mainFunc = binaryChunk.getMainFunc();
    // 0 params
    assertEquals(0, mainFunc.getNumParams());
    // 6 slots
    assertEquals(6, mainFunc.getMaxStackSize());
    // 1 upvalues
    assertEquals(1, binaryChunk.getSizeUpvalues());
    assertEquals(1, mainFunc.getUpvalues().length);
    // 5 locals
    // nothing

    // 4 constants
    assertEquals(4, mainFunc.getConstants().size());
    // 0 functions
    assertEquals(0, mainFunc.getProtos().length);

    LuaVM luaVM = LuaVM.create(mainFunc.getMaxStackSize(), mainFunc);
    {
      var instruction = luaVM.fetch();
      assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      assertEquals(Opcode.LOADK, instruction.getOpcode());
    }

    {
      var instruction = luaVM.fetch();
      assertEquals(Opcode.FORPREP, instruction.getOpcode());
      assertEquals(1, instruction.getOperand().A());
      assertEquals(4, instruction.getOperand().sBx());
    }

    assertEquals(Opcode.MOD, luaVM.fetch().getOpcode());
    assertEquals(Opcode.EQ, luaVM.fetch().getOpcode());
    assertEquals(Opcode.JMP, luaVM.fetch().getOpcode());
    assertEquals(Opcode.ADD, luaVM.fetch().getOpcode());
    assertEquals(Opcode.FORLOOP, luaVM.fetch().getOpcode());
    assertEquals(Opcode.RETURN, luaVM.fetch().getOpcode());
  }

  @Test
  void ch06SumLuac53OutEval() {
    BinaryChunk binaryChunk = BinaryChunk.of(ch06.sum.getLuacOut());
    var mainFunc = binaryChunk.getMainFunc();
    // stack的size大一些
    LuaVM luaVM = LuaVM.create(mainFunc.getMaxStackSize() * 2 + 8, mainFunc);

    // 提前配置好top
    var nRegisters = mainFunc.getMaxStackSize();
    luaVM.setTop(nRegisters);

    // LOADK
    fetchApplyPrint(luaVM);
    fetchApplyPrint(luaVM);
    fetchApplyPrint(luaVM);
    fetchApplyPrint(luaVM);

    // [0][1][100][1]
    assertEquals(LuaValue.of(0), luaVM.toLuaInteger(1));
    assertEquals(LuaValue.of(1), luaVM.toLuaInteger(2));
    assertEquals(LuaValue.of(100), luaVM.toLuaInteger(3));
    assertEquals(LuaValue.of(1), luaVM.toLuaInteger(4));

    // FORPREP
    fetchApplyPrint(luaVM);
    // FORLOOP
    fetchApplyPrint(luaVM);

    for (Instruction instruction = luaVM.fetch();
        !instruction.getOpcode().equals(Opcode.RETURN);
        instruction = luaVM.fetch()) {
      printLuaVM(luaVM);
      applyPrint(instruction, luaVM);
    }
    // [2550][101][100][1][100][0]
    assertEquals(LuaValue.of(2550), luaVM.toLuaInteger(1));
    assertEquals(LuaValue.of(101), luaVM.toLuaInteger(2));
    assertEquals(LuaValue.of(100), luaVM.toLuaInteger(3));
    assertEquals(LuaValue.of(1), luaVM.toLuaInteger(4));
    assertEquals(LuaValue.of(100), luaVM.toLuaInteger(5));
    assertEquals(LuaValue.of(0), luaVM.toLuaInteger(6));

    assertEquals(6, luaVM.getTop());
  }
}
