package ch08;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import constant.ResourceContentConstants.ch08;
import org.junit.jupiter.api.Test;

/**
 * 对{@link Opcode#VARARG}指令的测试
 *
 * @author wxq
 */
class VarargTest {

  @Test
  void varargCase1() {
    LuaVM.evalAndPrint(ch08.varargCase1);
  }

  @Test
  void varargCase2() {
    assertThrows(RuntimeException.class, () -> LuaVM.evalAndPrint(ch08.varargCase2));
  }
}
