package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * @author wxq
 */
public abstract class AbstractLuaAst implements LuaAst {
  private final LuaAstLocation location;

  protected AbstractLuaAst(LuaAstLocation location) {
    this.location = location;
  }

  /**
   * 缩进的个数必须传递到所有的{@link LuaAst}中.
   */
  private static int INDENT_COUNT = 0;

  private static final String INDENT = "\t";

  /**
   * 换行
   */
  protected void toLuaCodeNewline(PrintStream printStream) {
    printStream.println();
    // 补缩进
    for (int i = 0; i < INDENT_COUNT; i++) {
      printStream.print(INDENT);
    }
  }

  /**
   * 在缩进下，生成lua代码
   */
  protected void toLuaCodeIndent(PrintStream printStream, Consumer<PrintStream> toLuaCode) {
    // 新的一行缩进+1
    INDENT_COUNT++;
    this.toLuaCodeNewline(printStream);

    toLuaCode.accept(printStream);

    // 新的一行缩进-1
    INDENT_COUNT--;
    this.toLuaCodeNewline(printStream);
  }

  /**
   * do block end 模式 中间的block 需要缩进
   */
  protected void toLuaCodeDoBlockEnd(PrintStream printStream, Consumer<PrintStream> toLuaCode) {
    printStream.print(" do");
    // block
    this.toLuaCodeIndent(printStream, toLuaCode);
    printStream.print("end");
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{" + "location=" + location + '}';
  }

  @Override
  public LuaAstLocation getLocation() {
    return location;
  }
}
