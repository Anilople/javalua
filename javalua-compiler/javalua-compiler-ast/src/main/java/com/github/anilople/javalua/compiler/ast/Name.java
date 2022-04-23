package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
import lombok.Getter;

/**
 * 字符串字面量
 *
 * @author wxq
 */
@Getter
public class Name extends AbstractLuaAst {
  private final String identifier;

  public Name(LuaAstLocation luaAstLocation, String identifier) {
    super(luaAstLocation);
    this.identifier = identifier;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print(this.identifier);
  }
}
