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

  public LuaClosure(Prototype prototype) {
    this.prototype = prototype;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TFUNCTION;
  }
}
