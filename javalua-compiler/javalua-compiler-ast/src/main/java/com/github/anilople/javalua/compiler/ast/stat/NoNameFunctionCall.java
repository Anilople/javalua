package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;

/**
 * prefixexp args
 *
 * @author wxq
 */
public class NoNameFunctionCall extends FunctionCall {
  private final Args args;

  public NoNameFunctionCall(PrefixExp prefixExp, Args args) {
    super(prefixExp);
    this.args = args;
  }
}
