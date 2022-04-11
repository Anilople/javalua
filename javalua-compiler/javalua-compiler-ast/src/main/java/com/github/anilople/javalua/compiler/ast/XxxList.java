package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * 把 XxxList ::= Xxx {',' Xxx}的模式进行抽象，例如 varlist ::= var {‘,’ var}
 *
 * @author wxq
 */
public class XxxList<T> extends AbstractLuaAst {
  private final T t;
  private final List<T> list;
  public XxxList(LuaAstLocation luaAstLocation, T t, List<T> list) {
    super(luaAstLocation);
    this.t = t;
    this.list = list;
  }
}
