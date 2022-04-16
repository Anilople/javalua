package com.github.anilople.javalua.compiler.ast.stat;

/**
 *  ‘;’
 *
 * @author wxq
 */
public class EmptyStat implements Stat {

  private static final EmptyStat INSTANCE = new EmptyStat();

  private EmptyStat() {}

  public static EmptyStat getInstance() {
    return INSTANCE;
  }
}
