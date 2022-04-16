package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import java.util.Optional;

/**
 * parlist ::= namelist [‘,’ ‘...’] | ‘...’
 *
 * @author wxq
 */
public abstract class ParList extends AbstractLuaAst {
  public ParList(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  /**
   * namelist [‘,’ ‘...’]
   */
  public static class NameListParList extends ParList {
    private final NameList nameList;
    private final Optional<VarargExp> optionalVarargExp;

    public NameListParList(
        LuaAstLocation luaAstLocation, NameList nameList, Optional<VarargExp> optionalVarargExp) {
      super(luaAstLocation);
      this.nameList = nameList;
      this.optionalVarargExp = optionalVarargExp;
    }
  }

  /**
   * ‘...’
   */
  public static class VarargParList extends ParList {
    public VarargParList(LuaAstLocation luaAstLocation) {
      super(luaAstLocation);
    }
  }
}
