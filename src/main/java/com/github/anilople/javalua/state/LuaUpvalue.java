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
  //  private final boolean inStack;
  //  private final int index;

  //  static LuaUpvalue[] convert(Upvalue[] upvalues) {
  //    final int len = upvalues.length;
  //    LuaUpvalue[] luaUpvalues = new LuaUpvalue[upvalues.length];
  //    for (int i = 0; i < len; i++) {
  //      Upvalue upvalue = upvalues[i];
  //      LuaUpvalue luaUpvalue = new LuaUpvalue(luaValue, upvalue);
  //      luaUpvalues[i] = luaUpvalue;
  //    }
  //    return luaUpvalues;
  //  }

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

  //  public LuaUpvalue(Upvalue upvalue) {
  //    this.index = (int) upvalue.getIdx();
  //    if (1 == upvalue.getInstack()) {
  //      this.inStack = true;
  //    } else if (0 ==  upvalue.getInstack()) {
  //      this.inStack = false;
  //    } else {
  //      throw new IllegalStateException("upvalue in stack cannot be " + upvalue.getInstack());
  //    }
  //  }
  //  public boolean isInStack() {
  //    return this.inStack;
  //  }
  //  public int getIndex() {
  //    return this.index;
  //  }
}
