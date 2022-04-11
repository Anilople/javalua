package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;

/**
 * var ::=  Name | prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
 *
 * @author wxq
 */
public abstract class Var extends AbstractLuaAst {

  public Var(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * prefixexp ‘[’ exp ‘]’
   *
   * @author wxq
   */
  public static class PrefixExpVar extends Var {
    private final PrefixExp prefixExp;
    private final Exp exp;
    public PrefixExpVar(LuaAstLocation luaAstLocation,
        PrefixExp prefixExp, Exp exp) {
      super(luaAstLocation);
      this.prefixExp = prefixExp;
      this.exp = exp;
    }
  }

  /**
   * Name
   *
   * @author wxq
   */
  public static class NameVar extends Var {
    private final Name name;
    public NameVar(LuaAstLocation luaAstLocation, Name name) {
      super(luaAstLocation);
      this.name = name;
    }
  }

  /**
   * prefixexp ‘.’ Name
   *
   * @author wxq
   */
  public static class PrefixExpNameVar extends Var {
    private final PrefixExp prefixExp;
    private final Name name;
    public PrefixExpNameVar(LuaAstLocation luaAstLocation,
        PrefixExp prefixExp, Name name) {
      super(luaAstLocation);
      this.prefixExp = prefixExp;
      this.name = name;
    }
  }
}
