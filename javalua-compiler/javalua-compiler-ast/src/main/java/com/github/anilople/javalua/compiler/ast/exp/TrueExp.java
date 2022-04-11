package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import lombok.Data;

/**
 * @author wxq
 */
public class TrueExp extends AbstractExp {

  public TrueExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
