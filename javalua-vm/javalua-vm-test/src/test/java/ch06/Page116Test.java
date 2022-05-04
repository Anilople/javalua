package ch06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Opcode;
import com.github.anilople.javalua.state.LuaInteger;
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

    LuaVM luaVM = LuaVM.newLuaVM(mainFunc.getMaxStackSize(), mainFunc);
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
    LuaVM luaVM = LuaVM.newLuaVM(mainFunc.getMaxStackSize() * 2 + 8, mainFunc);

    // 提前配置好top
    var nRegisters = mainFunc.getMaxStackSize();
    luaVM.setTop(nRegisters);

    // LOADK
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);

    // [0][1][100][1]
    assertEquals(LuaInteger.newLuaInteger(0), luaVM.toLuaInteger(1));
    assertEquals(LuaInteger.newLuaInteger(1), luaVM.toLuaInteger(2));
    assertEquals(LuaInteger.newLuaInteger(100), luaVM.toLuaInteger(3));
    assertEquals(LuaInteger.newLuaInteger(1), luaVM.toLuaInteger(4));

    // FORPREP
    LuaVM.fetchApplyPrint(luaVM);
    // FORLOOP
    LuaVM.fetchApplyPrint(luaVM);

    for (Instruction instruction = luaVM.fetch();
        !instruction.getOpcode().equals(Opcode.RETURN);
        instruction = luaVM.fetch()) {
      LuaVM.printLuaVM(luaVM);
      LuaVM.applyPrint(instruction, luaVM);
    }
    // [2550][101][100][1][100][0]
    assertEquals(LuaInteger.newLuaInteger(2550), luaVM.toLuaInteger(1));
    assertEquals(LuaInteger.newLuaInteger(101), luaVM.toLuaInteger(2));
    assertEquals(LuaInteger.newLuaInteger(100), luaVM.toLuaInteger(3));
    assertEquals(LuaInteger.newLuaInteger(1), luaVM.toLuaInteger(4));
    assertEquals(LuaInteger.newLuaInteger(100), luaVM.toLuaInteger(5));
    assertEquals(LuaInteger.newLuaInteger(0), luaVM.toLuaInteger(6));

    assertEquals(6, luaVM.getTop());
  }
}
