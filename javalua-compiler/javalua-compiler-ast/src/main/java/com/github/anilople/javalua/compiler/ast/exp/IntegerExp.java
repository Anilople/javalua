package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import lombok.Getter;

/**
 * @author wxq
 */
@Getter
public class IntegerExp extends NumeralExp {
  private final long value;

  public IntegerExp(LuaAstLocation luaAstLocation, long value) {
    super(luaAstLocation);
    this.value = value;
  }
}
