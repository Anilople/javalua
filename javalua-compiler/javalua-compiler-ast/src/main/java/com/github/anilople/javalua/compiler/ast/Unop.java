package com.github.anilople.javalua.compiler.ast;

/**
 * unop ::= ‘-’ | not | ‘#’ | ‘~’
 *
 * @author wxq
 */
public abstract class Unop extends AbstractLuaAst {

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
  public static class LengthUnop extends Unop {
    public LengthUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }

  /**
   * ‘~’
   */
  public static class BitNotUnop extends Unop {
    public BitNotUnop(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }
}
