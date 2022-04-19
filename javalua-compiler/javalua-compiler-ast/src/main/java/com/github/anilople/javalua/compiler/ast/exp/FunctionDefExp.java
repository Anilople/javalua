package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import lombok.Getter;

/**
 * functiondef ::= function funcbody
 *
 * @author wxq
 */
@Getter
public class FunctionDefExp extends AbstractExp {
  private final FuncBody funcBody;

  public FunctionDefExp(LuaAstLocation luaAstLocation, FuncBody funcBody) {
    super(luaAstLocation);
    this.funcBody = funcBody;
  }
}
