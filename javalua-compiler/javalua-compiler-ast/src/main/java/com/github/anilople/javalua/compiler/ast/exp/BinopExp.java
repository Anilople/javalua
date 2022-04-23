package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.Binop;
import java.io.PrintStream;
import lombok.Getter;

/**
 * page 281
 *
 * exp binop exp
 *
 * @author wxq
 */
@Getter
public class BinopExp extends AbstractExp {
  private final Exp exp1;
  private final Binop binop;
  private final Exp exp2;

  public BinopExp(Exp exp1, Binop binop, Exp exp2) {
    super(exp1.getLocation());
    this.exp1 = exp1;
    this.binop = binop;
    this.exp2 = exp2;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    this.exp1.toLuaCode(printStream);
    printStream.print(' ');
    this.binop.toLuaCode(printStream);
    printStream.print(' ');
    this.exp2.toLuaCode(printStream);
  }
}
