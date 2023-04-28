package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.InstructionFactory;
import com.github.anilople.javalua.instruction.OpMode;
import com.github.anilople.javalua.instruction.Opcode;
import com.github.anilople.javalua.instruction.Operand;
import com.github.anilople.javalua.util.ByteUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxq
 */
class InstructionEmitterImpl implements InstructionEmitter {

  private final List<Instruction> instructions = new ArrayList<>();

  static void validOpMode(OpMode expectedOpMode, Opcode opcode) {
    if (!expectedOpMode.equals(opcode.getOpMode())) {
      throw new IllegalArgumentException(opcode + " is not op mode " + expectedOpMode);
    }
  }

  void emit(Opcode opcode, Operand operand) {
    int code = ByteUtils.clearLow6BitsOf(operand.getCodeValue()) | opcode.getOpcodeValue();
    Instruction instruction = InstructionFactory.newInstructionFactory().getInstructionBy(code);
    this.instructions.add(instruction);
  }

  void emitABC(Opcode opcode, int a, int b, int c) {
    validOpMode(OpMode.iABC, opcode);
    Operand operand = Operand.builder().iABC(b, c, a).build();
    this.emit(opcode, operand);
  }

  void emitABx(Opcode opcode, int a, int bx) {
    validOpMode(OpMode.iABx, opcode);
    Operand operand = Operand.builder().iABx(bx, a).build();
    this.emit(opcode, operand);
  }

  void emitAsBx(Opcode opcode, int a, int sBx) {
    validOpMode(OpMode.iAsBx, opcode);
    Operand operand = Operand.builder().iAsBx(sBx, a).build();
    this.emit(opcode, operand);
  }

  void emitAx(Opcode opcode, int ax) {
    validOpMode(OpMode.iAx, opcode);
    Operand operand = Operand.builder().iAx(ax).build();
    this.emit(opcode, operand);
  }

  @Override
  public void emitMove(int a, int b) {
    this.emitABC(Opcode.MOVE, a, b, 0);
  }

  @Override
  public void emitLoadNil(int a, int n) {
    this.emitABC(Opcode.LOADNIL, a, n, 0);
  }

  @Override
  public void emitLoadBool(int a, int b, int c) {
    this.emitABC(Opcode.LOADBOOL, a, b, c);
  }

  @Override
  public void emitLoadK(int a) {

  }

  @Override
  public void emitVararg(int a, int n) {

  }

  @Override
  public void emitClosure(int a, int bx) {

  }

  @Override
  public void emitNewTable(int a, int nArr, int nRec) {

  }

  @Override
  public void emitSetList(int a, int b, int c) {

  }

  @Override
  public void emitGetTable(int a, int b, int c) {

  }

  @Override
  public void emitSetTable(int a, int b, int c) {

  }

  @Override
  public void emitGetUpval(int a, int b) {

  }

  @Override
  public void emitSetUpval(int a, int b) {

  }

  @Override
  public void emitGetTabUp(int a, int b, int c) {

  }

  @Override
  public void emitSetTabUp(int a, int b, int c) {

  }

  @Override
  public void emitCall(int a, int nArgs, int nRet) {

  }

  @Override
  public void emitTailCall(int a, int nArgs) {

  }

  @Override
  public void emitReturn(int a, int n) {
    this.emitABC(Opcode.RETURN, a, n, 0);
  }

  @Override
  public void emitSelf(int a, int b, int c) {

  }

  @Override
  public int emitJmp(int a, int sBx) {
    return 0;
  }

  @Override
  public void emitTest(int a, int c) {

  }

  @Override
  public void emitTestSet(int a, int b, int c) {

  }

  @Override
  public int emitForPrep(int a, int sBx) {
    return 0;
  }

  @Override
  public int emitForLoop(int a, int sBx) {
    return 0;
  }

  @Override
  public void emitTForCall(int a, int c) {

  }

  @Override
  public void emitTForLoop(int a, int sBx) {

  }

  @Override
  public void emitUnaryOp(int op, int a, int b) {

  }

  @Override
  public void emitBinaryOp(int op, int a, int b, int c) {

  }
}
