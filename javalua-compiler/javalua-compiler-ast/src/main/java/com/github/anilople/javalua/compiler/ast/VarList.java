package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * varlist ::= var {‘,’ var}
 *
 * @author wxq
 */
public class VarList extends XxxList<Var> {
  public VarList(LuaAstLocation luaAstLocation, Var var, List<Var> list) {
    super(luaAstLocation, var, list);
  }
}
