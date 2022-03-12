package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import java.util.Arrays;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaClosure implements LuaValue {

  final Prototype prototype;
  final JavaFunction javaFunction;
  /**
   * 闭包要捕获父函数的局部变量
   */
  final LuaUpvalue[] luaUpvalues;

  public LuaClosure(Prototype prototype) {
    this.prototype = prototype;
    this.javaFunction = null;
    this.luaUpvalues = new LuaUpvalue[prototype.getUpvalues().length];
  }

  LuaClosure(JavaFunction javaFunction) {
    this(javaFunction, 0);
  }

  LuaClosure(JavaFunction javaFunction, int nUpvalues) {
    this.prototype = null;
    this.javaFunction = javaFunction;
    this.luaUpvalues = new LuaUpvalue[nUpvalues];
  }

  static LuaClosure newJavaFunction(JavaFunction javaFunction) {
    return new LuaClosure(javaFunction);
  }

  static LuaClosure newJavaClosure(JavaFunction javaFunction, int nUpvalues) {
    return new LuaClosure(javaFunction, nUpvalues);
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TFUNCTION;
  }

  public LuaUpvalue getLuaUpvalue(int index) {
    return this.luaUpvalues[index];
  }

  public void setLuaUpvalue(int index, LuaUpvalue luaUpvalue) {
    this.luaUpvalues[index] = luaUpvalue;
  }

  public void changeLuaUpvalueReferencedLuaValue(int index, LuaValue luaValue) {
    LuaUpvalue luaUpvalue = this.luaUpvalues[index];
    luaUpvalue.getSetter().accept(luaValue);
  }

  @Override
  public String toString() {
    return "LuaClosure{" +
        "prototype=" + "LineDefined " + (prototype == null ? null : prototype.getLineDefined()) +
        ", javaFunction=" + javaFunction +
        ", luaUpvalues=" + Arrays.toString(luaUpvalues) +
        '}';
  }
}
