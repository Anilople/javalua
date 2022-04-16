package com.github.anilople.javalua.compiler.ast;

import java.util.List;
import java.util.Optional;

/**
 * funcname ::= Name {‘.’ Name} [‘:’ Name]
 *
 * @author wxq
 */
public class FuncName extends AbstractLuaAst {

  private final Name name;
  /**
   * {‘.’ Name}
   */
  private final List<Name> dotNameList;
  /**
   * [‘:’ Name]
   */
  private final Optional<Name> optionalColonName;

  public FuncName(Name name, List<Name> dotNameList, Optional<Name> optionalColonName) {
    super(name.getLuaAstLocation());
    this.name = name;
    this.dotNameList = dotNameList;
    this.optionalColonName = optionalColonName;
  }
}
