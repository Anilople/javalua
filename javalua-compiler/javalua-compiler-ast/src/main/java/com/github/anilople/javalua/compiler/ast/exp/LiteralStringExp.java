package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;
import lombok.Getter;

/**
 * @author wxq
 */
@Getter
public class LiteralStringExp extends AbstractExp {
  private final String content;

  public LiteralStringExp(LuaAstLocation luaAstLocation, String content) {
    super(luaAstLocation);
    this.content = content;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print('"');
    printStream.print(this.content);
    printStream.print('"');
  }
}
