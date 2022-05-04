package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.state.LuaBoolean;

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
    this.operand = Operand.newOperand(originCodeValue);
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
  public void applyTo(LuaVM luaVM) {
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
  void binaryArithmeticOperator(LuaVM luaVM, ArithmeticOperator operator) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaVM.getRK(b);
    luaVM.getRK(c);
    luaVM.arithmetic(operator);
    luaVM.replace(a + 1);
  }

  void binaryBitwiseOperator(LuaVM luaVM, BitwiseOperator operator) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaVM.getRK(b);
    luaVM.getRK(c);
    luaVM.bitwise(operator);
    luaVM.replace(a + 1);
  }

  /**
   * 如果比较结果和操作数A（转换为布尔值）匹配，则跳过下一条指令。
   * <p/>不改变寄存器状态
   */
  void compare(LuaVM luaVM, ComparisonOperator operator) {
    final int b = operand.B();
    final int c = operand.C();
    luaVM.getRK(b);
    luaVM.getRK(c);

    var compareResult = luaVM.compare(-2, -1, operator);
    var expect = LuaBoolean.newLuaBoolean(operand.A() == 0);

    if (compareResult.equals(expect)) {
      luaVM.addPC(1);
    }

    luaVM.pop(2);
  }
}
