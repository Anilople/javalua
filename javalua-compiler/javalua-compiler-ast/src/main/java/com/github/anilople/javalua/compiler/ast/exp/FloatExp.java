package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import lombok.Getter;

/**
 * @author wxq
 */
@Getter
public class FloatExp extends NumeralExp {
  private final double value;

  public FloatExp(LuaAstLocation luaAstLocation, double value) {
    super(luaAstLocation);
    this.value = value;
  }
}
