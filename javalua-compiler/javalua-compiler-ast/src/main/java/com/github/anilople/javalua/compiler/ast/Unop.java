package com.github.anilople.javalua.compiler.ast;

/**
 * unop ::= ‘-’ | not | ‘#’ | ‘~’
 *
 * @author wxq
 */
public class Unop extends AbstractLuaAst {

  public Unop(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * ‘-’
   */
  public static class MinusUnop extends Unop {
    public MinusUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }

  /**
   * not
   */
  public static class NotUnop extends Unop {
    public NotUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }

  /**
   * ‘#’
   */
  public static class SharpUnop extends Unop {
    public SharpUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }

  /**
   * ‘~’
   */
  public static class TildeUnop extends Unop {
    public TildeUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }
}
