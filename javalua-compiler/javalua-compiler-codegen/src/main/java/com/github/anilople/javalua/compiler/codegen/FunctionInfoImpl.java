package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.InstructionFactory;
import com.github.anilople.javalua.instruction.OpMode;
import com.github.anilople.javalua.instruction.Opcode;
import com.github.anilople.javalua.instruction.Operand;
import com.github.anilople.javalua.util.ByteUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wxq
 */
class FunctionInfoImpl extends InstructionEmitterImpl implements FunctionInfo {

  /**
   * 变量的作用域层次
   */
  private final AtomicInteger scopeLevel = new AtomicInteger(0);

  private final FunctionInfo parent;
  /**
   * 所有子函数原型
   */
  private final List<FunctionInfo> children = new ArrayList<>();

  private final Map<String, UpvalueInfo> upvalues = new HashMap<>();

  private final Map<String, LocalVariableInfo> localVariableName2LocalVariableInfo = new HashMap<>();

  private final List<LocalVariableInfo> localVariables = new ArrayList<>();

  private final boolean isVararg;

  private final int numParams;

  FunctionInfoImpl(FunctionInfo parent, boolean isVararg, int numParams) {
    this.parent = parent;
    this.isVararg = isVararg;
    this.numParams = numParams;
  }

  @Override
  public FunctionInfo getSubFunctionInfo(int index) {
    return this.children.get(index);
  }

  @Override
  public int indexOfConstant(Object constant) {
    return 0;
  }

  @Override
  public int allocRegister() {
    return 0;
  }

  @Override
  public void freeRegister() {

  }

  @Override
  public int allocRegisters(int n) {
    return 0;
  }

  @Override
  public void freeRegisters(int n) {

  }

  @Override
  public void enterScope() {
    this.scopeLevel.incrementAndGet();
    // todo break
  }

  @Override
  public void enterLoopScope() {

  }

  @Override
  public void exitScope() {

  }

  @Override
  public void closeOpenUpvalues() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int addLocalVariable(String name) {
    return 0;
  }

  @Override
  public boolean existLocalVariable(String name) {
    return false;
  }

  @Override
  public int getIndexOfLocalVariable(String name) {
    return 0;
  }

  @Override
  public void removeLocalVariable(String name) {

  }

  @Override
  public boolean existsUpvalue(String name) {
    return false;
  }

  @Override
  public int bindUpvalue(String name) {
    return 0;
  }

  @Override
  public int indexOfUpvalue(String name) {
    return 0;
  }

  @Override
  public int pc() {
    return 0;
  }

  @Override
  public void fixSBx(int pc, int sBx) {

  }
}
