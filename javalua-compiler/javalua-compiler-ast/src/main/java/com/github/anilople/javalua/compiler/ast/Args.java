package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import java.util.Optional;

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
  public static class ExpListArgs extends Args {
    private final Optional<ExpList> optionalExpList;
    public ExpListArgs(LuaAstLocation luaAstLocation,
        Optional<ExpList> optionalExpList) {
      super(luaAstLocation);
      this.optionalExpList = optionalExpList;
    }
  }

  /**
   * tableconstructor
   *
   * @author wxq
   */
  public static class TableConstructorArgs extends Args {
    private final TableConstructorExp tableConstructorExp;

    public TableConstructorArgs(
        LuaAstLocation luaAstLocation,
        TableConstructorExp tableConstructorExp) {
      super(luaAstLocation);
      this.tableConstructorExp = tableConstructorExp;
    }
  }

  /**
   * LiteralString
   *
   * @author wxq
   */
  public static class LiteralStringArgs extends Args {
    private final LiteralStringExp literalStringExp;
    public LiteralStringArgs(LuaAstLocation luaAstLocation,
        LiteralStringExp literalStringExp) {
      super(luaAstLocation);
      this.literalStringExp = literalStringExp;
    }
  }
}
