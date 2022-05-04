package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import java.util.Arrays;

/**
 * @author wxq
 */
public class LuaClosureImpl implements LuaClosure {

  final Prototype prototype;
  final JavaFunction javaFunction;
  /**
   * 闭包要捕获父函数的局部变量
   */
  final LuaUpvalue[] luaUpvalues;

  public LuaClosureImpl() {
    throw new UnsupportedOperationException();
  }

  public LuaClosureImpl(Prototype prototype) {
    this.prototype = prototype;
    this.javaFunction = null;
    this.luaUpvalues = new LuaUpvalue[prototype.getUpvalues().length];
  }

  public LuaClosureImpl(JavaFunction javaFunction) {
    this(javaFunction, 0);
  }

  public LuaClosureImpl(JavaFunction javaFunction, int nUpvalues) {
    this.prototype = null;
    this.javaFunction = javaFunction;
    this.luaUpvalues = new LuaUpvalue[nUpvalues];
  }

  private boolean isValidUpvalueIndex(int upvalueIndex) {
    if (upvalueIndex < 0) {
      return false;
    }
    if (upvalueIndex >= this.luaUpvalues.length) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isPrototype() {
    return null != this.prototype;
  }

  public Prototype getPrototype() {
    return this.prototype;
  }

  @Override
  public boolean isJavaFunction() {
    return null != this.javaFunction;
  }

  @Override
  public JavaFunction getJavaFunction() {
    return this.javaFunction;
  }

  @Override
  public LuaValue getLuaValue(int upvalueIndex) {
    if (!this.isValidUpvalueIndex(upvalueIndex)) {
      return LuaValue.NIL;
    }
    LuaUpvalue luaUpvalue = this.luaUpvalues[upvalueIndex];
    return luaUpvalue.getLuaValue();
  }

  @Override
  public LuaUpvalue getLuaUpvalue(int upvalueIndex) {
    if (!this.isValidUpvalueIndex(upvalueIndex)) {
      throw new IllegalArgumentException("upvalueIndex " + upvalueIndex);
    }
    return this.luaUpvalues[upvalueIndex];
  }

  public void setLuaUpvalue(int index, LuaUpvalue luaUpvalue) {
    this.luaUpvalues[index] = luaUpvalue;
  }

  public void changeLuaUpvalueReferencedLuaValue(int upvalueIndex, LuaValue luaValue) {
    if (!this.isValidUpvalueIndex(upvalueIndex)) {
      return;
    }
    LuaUpvalue luaUpvalue = this.luaUpvalues[upvalueIndex];
    luaUpvalue.changeReferencedLuaValueTo(luaValue);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("LuaClosure{");
    if (null != this.prototype) {
      stringBuilder.append("prototype line LineDefined ").append(prototype.getLineDefined());
    }
    if (null != this.javaFunction) {
      stringBuilder.append("java function ").append(this.javaFunction);
    }
    stringBuilder.append(", luaUpvalues=").append(Arrays.toString(luaUpvalues));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}
