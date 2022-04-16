package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * Numeral
 *
 * page 280
 *
 * 为了简化代码生成器，把数字字面量表达式进一步分成了整数{@link IntegerExp}和浮点数{@link FloatExp}
 *
 * @author wxq
 */
public abstract class NumeralExp extends AbstractExp {

  public NumeralExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
