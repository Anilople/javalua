package com.github.anilople.javalua.state;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author wxq
 */
public class LuaUpvalueImpl implements LuaUpvalue {

  private Supplier<LuaValue> getter;
  private Consumer<LuaValue> setter;

  @Override
  public void init(Supplier<LuaValue> getter, Consumer<LuaValue> setter) {
    this.getter = getter;
    this.setter = setter;
  }

  public void changeReferencedLuaValueTo(LuaValue luaValue) {
    this.setter.accept(luaValue);
  }

  public LuaValue getLuaValue() {
    return this.getter.get();
  }

  @Override
  public String toString() {
    LuaValue luaValue = getter.get();
    final String luaValueString;
    if (luaValue instanceof LuaTable) {
      luaValueString = "table: " + Long.toHexString(luaValue.hashCode());
    } else {
      luaValueString = luaValue.toString();
    }
    return "LuaUpvalue{" + "luaValue=" + luaValueString + '}';
  }
}
