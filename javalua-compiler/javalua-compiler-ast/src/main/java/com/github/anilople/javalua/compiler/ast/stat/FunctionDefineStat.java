package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.FuncName;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;
import lombok.Getter;

/**
 * function funcname funcbody
 *
 * page 279
 *
 * @author wxq
 */
@Getter
public class FunctionDefineStat extends AbstractStat {
  private final FuncName funcName;
  private final FuncBody funcBody;

  public FunctionDefineStat(LuaAstLocation luaAstLocation, FuncName funcName, FuncBody funcBody) {
    super(luaAstLocation);
    this.funcName = funcName;
    this.funcBody = funcBody;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("function ");
    this.funcName.toLuaCode(printStream);
    this.funcBody.toLuaCode(printStream);
  }
}
