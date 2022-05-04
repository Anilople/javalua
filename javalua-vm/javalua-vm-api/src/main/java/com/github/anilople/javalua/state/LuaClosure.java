package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.util.SpiUtils;

/**
 * @author wxq
 */
public interface LuaClosure extends LuaValue {

  static LuaClosure newJavaFunctionLuaClosure(JavaFunction javaFunction, int nUpvalues) {
    return SpiUtils.loadOneInterfaceImpl(
        LuaClosure.class, JavaFunction.class, int.class, javaFunction, nUpvalues);
  }

  static LuaClosure newPrototypeLuaClosure(Prototype prototype) {
    return SpiUtils.loadOneInterfaceImpl(LuaClosure.class, Prototype.class, prototype);
  }

  @Override
  default LuaType type() {
    return LuaType.LUA_TFUNCTION;
  }

  boolean isPrototype();

  Prototype getPrototype();

  boolean isJavaFunction();

  JavaFunction getJavaFunction();

  /**
   * 根据upvalue的索引，获取对应的{@link LuaValue}
   *
   * @param upvalueIndex upvalue的索引
   * @return {@link LuaValue#NIL}如果越界
   */
  LuaValue getLuaValue(int upvalueIndex);

  LuaUpvalue getLuaUpvalue(int upvalueIndex);

  void setLuaUpvalue(int upvalueIndex, LuaUpvalue luaUpvalue);

  void changeLuaUpvalueReferencedLuaValue(int upvalueIndex, LuaValue luaValue);
}
