package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.io.PrintStream;
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

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.prefixExp.toLuaCode(printStream);
      printStream.print('[');
      this.exp.toLuaCode(printStream);
      printStream.print(']');
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

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.name.toLuaCode(printStream);
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

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.prefixExp.toLuaCode(printStream);
      printStream.print('.');
      this.name.toLuaCode(printStream);
    }
  }
}
