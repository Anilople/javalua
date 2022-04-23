package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;

/**
 * @author wxq
 */
public interface LuaAst {

  /**
   * @return ast节点所在位置
   */
  LuaAstLocation getLocation();

  /**
   * 将ast生成lua代码
   */
  void toLuaCode(PrintStream printStream);
}
