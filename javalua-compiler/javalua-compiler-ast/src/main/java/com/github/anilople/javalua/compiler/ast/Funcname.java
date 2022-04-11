package com.github.anilople.javalua.compiler.ast;

import java.util.List;
import java.util.Optional;

/**
 * funcname ::= Name {‘.’ Name} [‘:’ Name]
 *
 * @author wxq
 */
public class Funcname extends AbstractLuaAst {

  private final Name name;
  /**
   * {‘.’ Name}
   */
  private final List<Name> dotNameList;
  /**
   * [‘:’ Name]
   */
  private final Optional<Name> colonName;

  public Funcname(LuaAstLocation luaAstLocation, Name name,
      List<Name> dotNameList,
      Name colonName) {
    super(luaAstLocation);
    this.name = name;
    this.dotNameList = dotNameList;
    this.colonName = Optional.of(colonName);
  }

  public Funcname(LuaAstLocation luaAstLocation, Name name,
      List<Name> dotNameList) {
    super(luaAstLocation);
    this.name = name;
    this.dotNameList = dotNameList;
    this.colonName = Optional.empty();
  }
}
