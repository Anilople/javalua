package com.github.anilople.javalua.compiler.ast;

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
}
