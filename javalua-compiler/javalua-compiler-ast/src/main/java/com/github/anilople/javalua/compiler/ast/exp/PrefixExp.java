package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Var;
import com.github.anilople.javalua.compiler.ast.stat.FunctionCall;
import java.io.PrintStream;
import lombok.Getter;

/**
 * prefixexp ::= var | functioncall | ‘(’ exp ‘)’
 *
 * @author wxq
 */
public abstract class PrefixExp extends AbstractExp {

  public PrefixExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * var
   */
  @Getter
  public static class VarPrefixExp extends PrefixExp {
    private final Var var;

    public VarPrefixExp(Var var) {
      super(var.getLocation());
      this.var = var;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.var.toLuaCode(printStream);
    }
  }

  /**
   * functioncall
   */
  @Getter
  public static class FunctionCallPrefixExp extends PrefixExp {
    private final FunctionCall functionCall;

    public FunctionCallPrefixExp(FunctionCall functionCall) {
      super(functionCall.getLocation());
      this.functionCall = functionCall;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.functionCall.toLuaCode(printStream);
    }
  }

  /**
   * ‘(’ exp ‘)’
   */
  @Getter
  public static class ParenthesesPrefixExp extends PrefixExp {
    private final Exp exp;

    public ParenthesesPrefixExp(LuaAstLocation luaAstLocation, Exp exp) {
      super(luaAstLocation);
      this.exp = exp;
    }

    @Override
    public void toLuaCode(PrintStream printStream) {
      printStream.print('(');
      this.exp.toLuaCode(printStream);
      printStream.print(')');
    }
  }
}
