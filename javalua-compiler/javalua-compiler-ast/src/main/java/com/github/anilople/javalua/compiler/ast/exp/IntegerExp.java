package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;
import lombok.Getter;

/**
 * @author wxq
 */
@Getter
public class IntegerExp extends NumeralExp {
  private final long value;

  public IntegerExp(LuaAstLocation luaAstLocation, long value) {
    super(luaAstLocation);
    this.value = value;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print(this.value);
  }
}
