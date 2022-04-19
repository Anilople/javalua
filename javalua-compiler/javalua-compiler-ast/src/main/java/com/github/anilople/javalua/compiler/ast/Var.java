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
  public static class TableAccessByExpVar extends Var {
    private final Exp prefixExp;
    private final Exp exp;

    public TableAccessByExpVar(Exp prefixExp, Exp exp) {
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
  public static class TableAccessByNameVar extends Var {
    private final Exp prefixExp;
    private final Name name;

    public TableAccessByNameVar(Exp prefixExp, Name name) {
      super(prefixExp.getLocation());
      this.prefixExp = prefixExp;
      this.name = name;
    }
  }
}
