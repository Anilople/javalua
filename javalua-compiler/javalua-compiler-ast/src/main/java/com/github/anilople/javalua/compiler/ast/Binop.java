package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;

/**
 * <pre>
 * 	binop ::=  ‘+’ | ‘-’ | ‘*’ | ‘/’ | ‘//’ | ‘^’ | ‘%’ |
 * 		 ‘&’ | ‘~’ | ‘|’ | ‘>>’ | ‘<<’ | ‘..’ |
 * 		 ‘<’ | ‘<=’ | ‘>’ | ‘>=’ | ‘==’ | ‘~=’ |
 * 		 and | or
 * </pre>
 *
 * @author wxq
 */
public class Binop extends AbstractLuaAst {
  private final String symbol;

  public Binop(LuaAstLocation luaAstLocation, String symbol) {
    super(luaAstLocation);
    this.symbol = symbol;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print(this.symbol);
  }
}
