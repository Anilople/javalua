package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import java.util.Optional;
import lombok.Getter;

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
  @Getter
  public static class NameListParList extends ParList {
    private final NameList nameList;
    private final Optional<VarargExp> optionalVarargExp;

    public NameListParList(NameList nameList, Optional<VarargExp> optionalVarargExp) {
      super(nameList.getLocation());
      this.nameList = nameList;
      this.optionalVarargExp = optionalVarargExp;
    }
  }

  /**
   * ‘...’
   */
  public static class VarargParList extends ParList {
    private final VarargExp varargExp;

    public VarargParList(VarargExp varargExp) {
      super(varargExp.getLocation());
      this.varargExp = varargExp;
    }
  }
}
