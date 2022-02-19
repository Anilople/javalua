package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.state.DefaultLuaStateImpl;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaValue;

/**
 * page 93
 *
 * @author wxq
 */
class DefaultLuaVMImpl extends DefaultLuaStateImpl implements LuaVM {

  DefaultLuaVMImpl(int stackSize, Prototype prototype) {
    super(stackSize, prototype);
  }


  @Override
  public int pc() {
    return this.callStack.topCallFrame().getPc();
  }

  @Override
  public void addPC(int n) {
    this.callStack.topCallFrame().addPc(n);
  }

  @Override
  public Instruction fetch() {
    var instruction = this.instructions[this.pc()];
    this.addPC(1);
    return instruction;
  }

  @Override
  public void getConst(int index) {
    var constants = this.prototype.getConstants();
    var constant = constants.getConstant(index);
    var luaValue = LuaValue.of(constant);
    this.callStack.topCallFrame().push(luaValue);
  }

  @Override
  public void getRK(int rk) {
    if (rk > 0xFF) {
      // 常量表索引
      this.getConst(rk & 0xFF);
    } else {
      // 寄存器索引
      this.pushValue(rk + 1);
    }
  }

  @Override
  protected void runLuaClosure() {
    for (var instruction = this.fetch(); ; instruction = this.fetch()) {
      if (Opcode.RETURN.equals(instruction.getOpcode())) {
        // TODO, fix me
        break;
      }
      instruction.applyTo(this);
    }
  }

  @Override
  public int getRegisterCount() {
    var luaClosure = this.callStack.topCallFrame().getLuaClosure();
    return luaClosure.getPrototype().getMaxStackSize();
  }

  @Override
  public void loadVararg(int numberOfVararg) {
    var callFrame = this.callStack.topCallFrame();
    throw new UnsupportedOperationException();
  }

  @Override
  public void loadPrototype(int index) {
    var prototype = this.prototype.getProtos()[index];
    LuaClosure luaClosure = new LuaClosure(prototype);
    this.callStack.topCallFrame().push(luaClosure);
  }
}
