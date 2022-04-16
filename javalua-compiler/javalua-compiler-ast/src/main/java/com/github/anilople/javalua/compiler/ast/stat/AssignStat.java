package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.VarList;

/**
 * varlist ‘=’ explist
 *
 * @author wxq
 */
public class AssignStat extends AbstractStat {

  private final VarList varList;
  private final ExpList expList;

  public AssignStat(VarList varList, ExpList expList) {
    super(varList.getLuaAstLocation());
    this.varList = varList;
    this.expList = expList;
  }
}
