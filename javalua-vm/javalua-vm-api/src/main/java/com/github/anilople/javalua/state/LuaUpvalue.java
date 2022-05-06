package com.github.anilople.javalua.state;

import com.github.anilople.javalua.util.SpiUtils;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author wxq
 */
public interface LuaUpvalue {

  static LuaUpvalue newLuaUpvalue(Supplier<LuaValue> getter, Consumer<LuaValue> setter) {
    return SpiUtils.loadOneInterfaceImpl(
        LuaUpvalue.class, Supplier.class, Consumer.class, getter, setter);
  }

  static LuaUpvalue newFixedLuaUpvalue(LuaValue luaValue) {
    return LuaUpvalue.newLuaUpvalue(
        () -> luaValue,
        luaValue1 -> {
          throw new UnsupportedOperationException();
        });
  }

  LuaValue getLuaValue();

  void changeReferencedLuaValueTo(LuaValue luaValue);
}
