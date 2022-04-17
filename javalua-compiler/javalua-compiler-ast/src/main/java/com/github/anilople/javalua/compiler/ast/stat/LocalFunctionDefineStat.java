package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import lombok.Getter;

/**
 * local function Name funcbody
 *
 * @see FunctionDefineStat
 * @author wxq
 */
@Getter
public class LocalFunctionDefineStat extends AbstractStat {
  private final Name name;
  private final FuncBody funcbody;

  public LocalFunctionDefineStat(LuaAstLocation luaAstLocation, Name name, FuncBody funcbody) {
    super(luaAstLocation);
    this.name = name;
    this.funcbody = funcbody;
  }
}
