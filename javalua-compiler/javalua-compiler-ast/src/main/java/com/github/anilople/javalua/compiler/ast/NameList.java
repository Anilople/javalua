package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * namelist ::= Name {‘,’ Name}
 *
 * @author wxq
 */
public class NameList extends XxxList<Name> {
  public NameList(LuaAstLocation luaAstLocation, Name name,
      List<Name> list) {
    super(luaAstLocation, name, list);
  }
}
