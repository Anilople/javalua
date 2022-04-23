package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.VarList;
import java.io.PrintStream;

/**
 * varlist ‘=’ explist
 *
 * @author wxq
 */
public class AssignStat extends AbstractStat {

  private final VarList varList;
  private final ExpList expList;

  public AssignStat(VarList varList, ExpList expList) {
    super(varList.getLocation());
    this.varList = varList;
    this.expList = expList;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    this.varList.toLuaCode(printStream);
    printStream.print(" = ");
    this.expList.toLuaCode(printStream);
  }
}
