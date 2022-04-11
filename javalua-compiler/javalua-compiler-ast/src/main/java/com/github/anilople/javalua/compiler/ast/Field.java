package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.Exp;

/**
 * field ::= ‘[’ exp ‘]’ ‘=’ exp | Name ‘=’ exp | exp
 *
 * @author wxq
 */
public abstract class Field extends AbstractLuaAst {

  public Field(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * ‘[’ exp ‘]’ ‘=’ exp
   */
  public static class TableField extends Field {
    private final Exp expInSquare;
    private final Exp exp;

    public TableField(LuaAstLocation luaAstLocation, Exp expInSquare, Exp exp) {
      super(luaAstLocation);
      this.expInSquare = expInSquare;
      this.exp = exp;
    }
  }

  /**
   * Name ‘=’ exp
   */
  public static class NameField extends Field {
    private final Name name;
    private final Exp exp;

    public NameField(LuaAstLocation luaAstLocation, Name name, Exp exp) {
      super(luaAstLocation);
      this.name = name;
      this.exp = exp;
    }
  }

  /**
   * exp
   */
  public static class ExpField extends Field {
    private final Exp exp;

    public ExpField(LuaAstLocation luaAstLocation, Exp exp) {
      super(luaAstLocation);
      this.exp = exp;
    }
  }
}
