package ch06;

import static com.github.anilople.javalua.api.LuaVM.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.state.LuaValue;
import constant.ResourceContentConstants.ch06;
import org.junit.jupiter.api.Assertions;
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
    Assertions.assertEquals(1, binaryChunk.getSizeUpvalues());

    var mainFunc = binaryChunk.getMainFunc();
    // 0 params
    Assertions.assertEquals(0, mainFunc.getNumParams());
    // 6 slots
    Assertions.assertEquals(6, mainFunc.getMaxStackSize());
    // 1 upvalues
    Assertions.assertEquals(1, binaryChunk.getSizeUpvalues());
    Assertions.assertEquals(1, mainFunc.getUpvalues().length);
    // 5 locals
    // nothing

    // 4 constants
    Assertions.assertEquals(4, mainFunc.getConstants().size());
    // 0 functions
    Assertions.assertEquals(0, mainFunc.getProtos().length);

    LuaVM luaVM = LuaVM.create(mainFunc.getMaxStackSize(), mainFunc);
    {
      var instruction = luaVM.fetch();
      Assertions.assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      Assertions.assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      Assertions.assertEquals(Opcode.LOADK, instruction.getOpcode());
    }
    {
      var instruction = luaVM.fetch();
      Assertions.assertEquals(Opcode.LOADK, instruction.getOpcode());
    }

    {
      var instruction = luaVM.fetch();
      Assertions.assertEquals(Opcode.FORPREP, instruction.getOpcode());
      Assertions.assertEquals(1, instruction.getOperand().A());
      Assertions.assertEquals(4, instruction.getOperand().sBx());
    }

    Assertions.assertEquals(Opcode.MOD, luaVM.fetch().getOpcode());
    Assertions.assertEquals(Opcode.EQ, luaVM.fetch().getOpcode());
    Assertions.assertEquals(Opcode.JMP, luaVM.fetch().getOpcode());
    Assertions.assertEquals(Opcode.ADD, luaVM.fetch().getOpcode());
    Assertions.assertEquals(Opcode.FORLOOP, luaVM.fetch().getOpcode());
    Assertions.assertEquals(Opcode.RETURN, luaVM.fetch().getOpcode());
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
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);
    LuaVM.fetchApplyPrint(luaVM);

    // [0][1][100][1]
    Assertions.assertEquals(LuaValue.of(0), luaVM.toLuaInteger(1));
    Assertions.assertEquals(LuaValue.of(1), luaVM.toLuaInteger(2));
    Assertions.assertEquals(LuaValue.of(100), luaVM.toLuaInteger(3));
    Assertions.assertEquals(LuaValue.of(1), luaVM.toLuaInteger(4));

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
    Assertions.assertEquals(LuaValue.of(2550), luaVM.toLuaInteger(1));
    Assertions.assertEquals(LuaValue.of(101), luaVM.toLuaInteger(2));
    Assertions.assertEquals(LuaValue.of(100), luaVM.toLuaInteger(3));
    Assertions.assertEquals(LuaValue.of(1), luaVM.toLuaInteger(4));
    Assertions.assertEquals(LuaValue.of(100), luaVM.toLuaInteger(5));
    Assertions.assertEquals(LuaValue.of(0), luaVM.toLuaInteger(6));

    Assertions.assertEquals(6, luaVM.getTop());
  }
}
