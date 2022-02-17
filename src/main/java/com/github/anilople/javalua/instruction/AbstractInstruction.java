package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

/**
 * @author wxq
 */
abstract class AbstractInstruction implements Instruction {

  protected final int originCodeValue;
  protected final Opcode opcode;
  protected final Operand operand;

  public AbstractInstruction(int originCodeValue) {
    this.originCodeValue = originCodeValue;
    this.opcode = Opcode.of(originCodeValue);
    this.operand = Operand.of(originCodeValue);
  }

  @Override
  public Opcode getOpcode() {
    return this.opcode;
  }

  @Override
  public Operand getOperand() {
    return this.operand;
  }

  @Override
  public void applyTo(LuaState luaState) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    String opcodePart = this.opcode + "";
    StringBuilder operandPart = new StringBuilder();
    switch (this.opcode.getOpMode()) {
      case iABC:
        operandPart.append("B ").append(operand.B());
        operandPart.append(" C ").append(operand.C());
        operandPart.append(" A ").append(operand.A());
        break;
      case iABx:
        operandPart.append("Bx ").append(operand.Bx());
        operandPart.append(" A ").append(operand.A());
        break;
      case iAsBx:
        operandPart.append("sBx ").append(operand.sBx());
        operandPart.append(" A ").append(operand.A());
        break;
      case iAx:
        operandPart.append("Ax ").append(operand.Ax());
        break;
      default:
        throw new IllegalStateException("unknown op mode " + this.opcode.getOpMode());
    }
    return opcodePart + "[" + operandPart + "]";
  }

  String toStringHelper(String middleContent) {
    return this.opcode + "[" + middleContent + "]";
  }

  /**
   * 二元运算
   */
  void binaryArithmeticOperator(LuaState luaState, ArithmeticOperator operator) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaState.getRK(b);
    luaState.getRK(c);
    luaState.arithmetic(operator);
    luaState.replace(a + 1);
  }

  void binaryBitwiseOperator(LuaState luaState, BitwiseOperator operator) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaState.getRK(b);
    luaState.getRK(c);
    luaState.bitwise(operator);
    luaState.replace(a + 1);
  }

  /**
   * 如果比较结果和操作数A（转换为布尔值）匹配，则跳过下一条指令。
   * <p/>不改变寄存器状态
   */
  void compare(LuaState luaState, ComparisonOperator operator) {
    luaState.getRK(operand.B());
    luaState.getRK(operand.C());

    var compareResult = luaState.compare(-2, -1, operator);
    var expect = LuaValue.of(operand.A() == 0);

    if (compareResult.equals(expect)) {
      luaState.addPC(1);
    }

    luaState.pop(2);
  }
}
