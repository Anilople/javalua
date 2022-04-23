package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import java.io.PrintStream;
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

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.nameList.toLuaCode(printStream);
      if (this.optionalVarargExp.isPresent()) {
        printStream.print(',');
        this.optionalVarargExp.get().toLuaCode(printStream);
      }
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

    @Override
    public void toLuaCode(PrintStream printStream) {
      this.varargExp.toLuaCode(printStream);
    }
  }
}
