package com.github.anilople.javalua.state;

import com.github.anilople.javalua.util.SpiUtils;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Data;

/**
 * @author wxq
 */
public interface LuaUpvalue {

  static LuaUpvalue newLuaUpvalue(Supplier<LuaValue> getter, Consumer<LuaValue> setter) {
    LuaUpvalue luaUpvalue = SpiUtils.loadOneInterfaceImpl(LuaUpvalue.class);
    luaUpvalue.init(getter, setter);
    return luaUpvalue;
  }

  static void unsupportedConsume(LuaValue luaValue) {
    throw new UnsupportedOperationException();
  }

  static LuaUpvalue newFixedLuaUpvalue(LuaValue luaValue) {
    return LuaUpvalue.newLuaUpvalue(() -> luaValue, LuaUpvalue::unsupportedConsume);
  }

  void init(Supplier<LuaValue> getter, Consumer<LuaValue> setter);

  void changeReferencedLuaValueTo(LuaValue luaValue);

  LuaValue getLuaValue();
}
