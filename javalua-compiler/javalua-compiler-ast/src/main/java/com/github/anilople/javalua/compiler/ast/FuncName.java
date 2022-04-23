package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

/**
 * funcname ::= Name {‘.’ Name} [‘:’ Name]
 *
 * @author wxq
 */
@Getter
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
    super(name.getLocation());
    this.name = name;
    this.dotNameList = dotNameList;
    this.optionalColonName = optionalColonName;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    this.name.toLuaCode(printStream);
    for (Name name : this.dotNameList) {
      printStream.print('.');
      name.toLuaCode(printStream);
    }
    if (this.optionalColonName.isPresent()) {
      printStream.print(':');
      this.optionalColonName.get().toLuaCode(printStream);
    }
  }
}
