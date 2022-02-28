package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaClosure implements LuaValue {

  final Prototype prototype;
  final JavaFunction javaFunction;

  public LuaClosure(Prototype prototype) {
    this.prototype = prototype;
    this.javaFunction = null;
  }

  public LuaClosure(JavaFunction javaFunction) {
    this.prototype = null;
    this.javaFunction = javaFunction;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TFUNCTION;
  }
}
