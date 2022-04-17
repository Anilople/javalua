package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import lombok.Getter;

/**
 * prefixexp args
 *
 * @author wxq
 */
@Getter
public class NoNameFunctionCall extends FunctionCall {
  private final Args args;

  public NoNameFunctionCall(PrefixExp prefixExp, Args args) {
    super(prefixExp);
    this.args = args;
  }
}
