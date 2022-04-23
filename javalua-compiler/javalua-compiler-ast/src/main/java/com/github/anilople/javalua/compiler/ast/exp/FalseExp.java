package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;

/**
 * @author wxq
 */
public class FalseExp extends AbstractExp {

  public FalseExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("false");
  }
}
