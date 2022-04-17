package com.github.anilople.javalua.compiler.ast;

import java.util.List;
import lombok.Getter;

/**
 * 把 XxxList ::= Xxx {',' Xxx}的模式进行抽象，例如 varlist ::= var {‘,’ var}
 *
 * @author wxq
 */
@Getter
public class XxxList<T extends LuaAst> extends AbstractLuaAst {
  private final T first;
  private final List<T> tail;

  public XxxList(T first, List<T> tail) {
    super(first.getLocation());
    this.first = first;
    this.tail = tail;
  }

  public int size() {
    return 1 + tail.size();
  }
}
