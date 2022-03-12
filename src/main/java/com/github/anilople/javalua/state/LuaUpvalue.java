package com.github.anilople.javalua.state;

import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaUpvalue {
  private final Supplier<LuaValue> getter;
  private final Consumer<LuaValue> setter;

  static void unsupportedConsume(LuaValue luaValue) {
    throw new UnsupportedOperationException();
  }

  static LuaUpvalue newFixedLuaUpvalue(LuaValue luaValue) {
    return new LuaUpvalue(() -> luaValue, LuaUpvalue::unsupportedConsume);
  }

  public LuaUpvalue(Supplier<LuaValue> getter, Consumer<LuaValue> setter) {
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
    return "LuaUpvalue{" + "luaValue=" + getter.get() + '}';
  }
}
