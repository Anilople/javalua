package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import lombok.Getter;

/**
 * prefixexp ‘:’ Name args
 *
 * @author wxq
 */
@Getter
public class NameFunctionCall extends FunctionCall {
  private final Name name;
  private final Args args;

  public NameFunctionCall(PrefixExp prefixExp, Name name, Args args) {
    super(prefixExp);
    this.name = name;
    this.args = args;
  }
}
