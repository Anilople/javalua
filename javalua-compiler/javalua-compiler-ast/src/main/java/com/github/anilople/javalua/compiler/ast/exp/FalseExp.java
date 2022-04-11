package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import lombok.Data;

/**
 * @author wxq
 */
public class FalseExp extends AbstractExp {

  public FalseExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
