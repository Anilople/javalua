package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.Unop;
import java.io.PrintStream;
import lombok.Getter;

/**
 * page 281
 *
 * unop exp
 *
 * @author wxq
 */
@Getter
public class UnopExp extends AbstractExp {
  private final Unop unop;
  private final Exp exp;

  public UnopExp(Unop unop, Exp exp) {
    super(unop.getLocation());
    this.unop = unop;
    this.exp = exp;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    this.unop.toLuaCode(printStream);
    printStream.print(" ");
    this.exp.toLuaCode(printStream);
  }
}
