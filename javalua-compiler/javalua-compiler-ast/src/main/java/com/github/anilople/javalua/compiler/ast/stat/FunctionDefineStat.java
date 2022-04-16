package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.FuncName;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * function funcname funcbody
 *
 * page 279
 *
 * @author wxq
 */
public class FunctionDefineStat extends AbstractStat {
  private final FuncName funcname;
  private final FuncBody funcbody;

  public FunctionDefineStat(LuaAstLocation luaAstLocation,
      FuncName funcname, FuncBody funcbody) {
    super(luaAstLocation);
    this.funcname = funcname;
    this.funcbody = funcbody;
  }
}
