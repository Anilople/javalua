package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.state.DefaultLuaStateImpl;
import com.github.anilople.javalua.state.LuaValue;

/**
 * page 93
 *
 * @author wxq
 */
class DefaultLuaVMImpl extends DefaultLuaStateImpl implements LuaVM {

  private final Instruction[] instructions;

  DefaultLuaVMImpl(int stackSize, Prototype prototype) {
    super(stackSize, prototype);
    this.instructions = this.prototype.getCode().getInstructions();
  }

  @Override
  public int pc() {
    return this.pc;
  }

  @Override
  public void addPC(int n) {
    this.pc += n;
  }

  @Override
  public Instruction fetch() {
    var instruction = this.instructions[this.pc];
    this.addPC(1);
    return instruction;
  }

  @Override
  public void getConst(int index) {
    var constants = this.prototype.getConstants();
    var constant = constants.getConstant(index);
    var luaValue = LuaValue.of(constant);
    this.luaStack.push(luaValue);
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
}
