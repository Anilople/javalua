package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.Name;

/**
 * prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
 *
 * @author wxq
 * @see com.github.anilople.javalua.compiler.ast.Var
 */
public class TableAccessExp extends AbstractExp {
  private final PrefixExp prefixExp;
  private final Exp keyExp;

  public TableAccessExp(PrefixExp prefixExp, Exp keyExp) {
    super(prefixExp.getLocation());
    this.prefixExp = prefixExp;
    this.keyExp = keyExp;
  }

  public TableAccessExp(PrefixExp prefixExp, Name key) {
    super(prefixExp.getLocation());
    this.prefixExp = prefixExp;
    this.keyExp = new LiteralStringExp(key.getLocation(), key.getIdentifier());
  }
}
