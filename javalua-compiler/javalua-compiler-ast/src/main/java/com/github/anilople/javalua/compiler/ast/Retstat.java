package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
import java.util.Optional;
import lombok.Getter;

/**
 * retstat ::= return [explist] [‘;’]
 *
 * @author wxq
 */
@Getter
public class Retstat extends AbstractLuaAst {
  private final Optional<ExpList> optionalExpList;

  public Retstat(LuaAstLocation luaAstLocation, Optional<ExpList> optionalExpList) {
    super(luaAstLocation);
    this.optionalExpList = optionalExpList;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("return");
    printStream.print(" ");
    if (this.optionalExpList.isPresent()) {
      ExpList expList = this.optionalExpList.get();
      expList.toLuaCode(printStream);
    }
  }
}
