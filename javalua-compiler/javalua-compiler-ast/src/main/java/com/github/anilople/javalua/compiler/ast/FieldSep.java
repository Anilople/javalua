package com.github.anilople.javalua.compiler.ast;

/**
 * fieldsep ::= ‘,’ | ‘;’
 *
 * @author wxq
 */
public abstract class FieldSep extends AbstractLuaAst {

  public FieldSep(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * ‘,’
   */
  public static class CommaFieldSep extends FieldSep {

    public CommaFieldSep(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }

  /**
   * ‘;’
   */
  public static class SemicolonFieldSep extends FieldSep {

    public SemicolonFieldSep(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }
}
