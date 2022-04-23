package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
import java.util.Optional;
import lombok.Getter;

/**
 * funcbody ::= ‘(’ [parlist] ‘)’ block end
 *
 * @author wxq
 */
@Getter
public class FuncBody extends AbstractLuaAst {
  private final Optional<ParList> optionalParList;
  private final Block block;

  public FuncBody(LuaAstLocation luaAstLocation, Optional<ParList> optionalParList, Block block) {
    super(luaAstLocation);
    this.optionalParList = optionalParList;
    this.block = block;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print('(');
    if (this.optionalParList.isPresent()) {
      ParList parList = this.optionalParList.get();
      parList.toLuaCode(printStream);
    }
    printStream.print(')');
    this.toLuaCodeIndent(printStream, this.block::toLuaCode);
    printStream.print("end");
  }
}
