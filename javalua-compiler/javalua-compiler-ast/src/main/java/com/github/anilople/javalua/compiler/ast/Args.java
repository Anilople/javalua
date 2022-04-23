package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import java.io.PrintStream;
import java.util.Optional;
import lombok.Getter;

/**
 * args ::=  ‘(’ [explist] ‘)’ | tableconstructor | LiteralString
 *
 * @author wxq
 */
public abstract class Args extends AbstractLuaAst {

  public Args(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * ‘(’ [explist] ‘)’
   *
   * @author wxq
   */
  @Getter
  public static class ExpListArgs extends Args {
    private final Optional<ExpList> optionalExpList;

    public ExpListArgs(LuaAstLocation luaAstLocation, Optional<ExpList> optionalExpList) {
      super(luaAstLocation);
      this.optionalExpList = optionalExpList;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      printStream.print('(');
      if (this.optionalExpList.isPresent()) {
        ExpList expList = this.optionalExpList.get();
        expList.toLuaCode(printStream);
      }
      printStream.print(')');
    }
  }

  /**
   * tableconstructor
   *
   * @author wxq
   */
  @Getter
  public static class TableConstructorArgs extends Args {
    private final TableConstructorExp tableConstructorExp;

    public TableConstructorArgs(TableConstructorExp tableConstructorExp) {
      super(tableConstructorExp.getLocation());
      this.tableConstructorExp = tableConstructorExp;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.tableConstructorExp.toLuaCode(printStream);
    }
  }

  /**
   * LiteralString
   *
   * @author wxq
   */
  @Getter
  public static class LiteralStringArgs extends Args {
    private final LiteralStringExp literalStringExp;

    public LiteralStringArgs(LiteralStringExp literalStringExp) {
      super(literalStringExp.getLocation());
      this.literalStringExp = literalStringExp;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.literalStringExp.toLuaCode(printStream);
    }
  }
}
