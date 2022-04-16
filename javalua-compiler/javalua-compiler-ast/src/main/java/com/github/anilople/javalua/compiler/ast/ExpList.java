package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.util.List;

/**
 * explist ::= exp {‘,’ exp}
 *
 * @author wxq
 */
public class ExpList extends XxxList<Exp> {

  public ExpList(Exp first, List<Exp> tail) {
    super(first, tail);
  }
}
