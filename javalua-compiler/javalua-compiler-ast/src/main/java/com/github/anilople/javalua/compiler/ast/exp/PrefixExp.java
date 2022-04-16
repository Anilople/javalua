package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Var;
import com.github.anilople.javalua.compiler.ast.stat.FunctionCall;

/**
 * prefixexp ::= var | functioncall | ‘(’ exp ‘)’
 *
 * @author wxq
 */
public class PrefixExp extends AbstractExp {

  public PrefixExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * var
   */
  public static class VarPrefixExp extends PrefixExp {
    private final Var var;
    public VarPrefixExp(Var var) {
      super(var.getLuaAstLocation());
      this.var = var;
    }
  }

  /**
   * functioncall
   */
  public static class FunctionCallPrefixExp extends PrefixExp {
    private final FunctionCall functionCall;
    public FunctionCallPrefixExp(FunctionCall functionCall) {
      super(functionCall.getLuaAstLocation());
      this.functionCall = functionCall;
    }
  }

  /**
   * ‘(’ exp ‘)’
   */
  public static class ParenthesesPrefixExp extends PrefixExp {
    private final Exp exp;
    public ParenthesesPrefixExp(LuaAstLocation luaAstLocation,
        Exp exp) {
      super(luaAstLocation);
      this.exp = exp;
    }
  }
}
