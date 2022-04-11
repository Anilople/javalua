package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;

/**
 * prefixexp ‘:’ Name args
 *
 * @author wxq
 */
public class NameFunctionCall extends FunctionCall {
  private final PrefixExp prefixExp;
  private final Name name;
  private final Args args;
  public NameFunctionCall(LuaAstLocation luaAstLocation,
      PrefixExp prefixExp, Name name, Args args) {
    super(luaAstLocation);
    this.prefixExp = prefixExp;
    this.name = name;
    this.args = args;
  }
}
