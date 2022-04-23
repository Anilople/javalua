package com.github.anilople.javalua.compiler.ast;

import java.io.PrintStream;
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

  /**
   * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index >= size())
   */
  public T get(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("index " + index);
    }
    if (index == 0) {
      return first;
    }
    return this.tail.get(index - 1);
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    this.getFirst().toLuaCode(printStream);
    for (LuaAst luaAst : this.tail) {
      printStream.print(',');
      luaAst.toLuaCode(printStream);
    }
  }
}
