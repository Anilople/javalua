package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * varlist ::= var {‘,’ var}
 *
 * @author wxq
 */
public class VarList extends XxxList<Var> {
  public VarList(Var first, List<Var> tail) {
    super(first, tail);
  }
}
