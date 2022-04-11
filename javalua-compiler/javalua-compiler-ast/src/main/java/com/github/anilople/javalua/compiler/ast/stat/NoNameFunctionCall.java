package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;

/**
 * @author wxq
 */
public class NoNameFunctionCall extends FunctionCall {
  private final PrefixExp prefixExp;
  private final Args args;

  public NoNameFunctionCall(LuaAstLocation luaAstLocation, PrefixExp prefixExp, Args args) {
    super(luaAstLocation);
    this.prefixExp = prefixExp;
    this.args = args;
  }
}
