package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * @author wxq
 */
public class FloatExp extends NumeralExp {
  private final double value;

  public FloatExp(LuaAstLocation luaAstLocation, double value) {
    super(luaAstLocation);
    this.value = value;
  }
}
