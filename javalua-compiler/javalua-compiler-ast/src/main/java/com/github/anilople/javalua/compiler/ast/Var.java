package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.Exp;
import lombok.Getter;

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
   * <p>
   * table access
   *
   * @author wxq
   */
  @Getter
  public static class PrefixExpVar extends Var {
    private final Exp prefixExp;
    private final Exp exp;

    public PrefixExpVar(Exp prefixExp, Exp exp) {
      super(prefixExp.getLocation());
      this.prefixExp = prefixExp;
      this.exp = exp;
    }
  }

  /**
   * Name
   *
   * @author wxq
   */
  @Getter
  public static class NameVar extends Var {
    private final Name name;

    public NameVar(Name name) {
      super(name.getLocation());
      this.name = name;
    }
  }

  /**
   * prefixexp ‘.’ Name
   *
   * <p>
   * table access
   *
   * @author wxq
   */
  @Getter
  public static class PrefixExpNameVar extends Var {
    private final Exp prefixExp;
    private final Name name;

    public PrefixExpNameVar(Exp prefixExp, Name name) {
      super(prefixExp.getLocation());
      this.prefixExp = prefixExp;
      this.name = name;
    }
  }
}
