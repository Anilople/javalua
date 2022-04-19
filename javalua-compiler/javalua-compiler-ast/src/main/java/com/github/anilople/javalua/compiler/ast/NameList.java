package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * namelist ::= Name {‘,’ Name}
 *
 * @author wxq
 */
public class NameList extends XxxList<Name> {
  public NameList(Name first, List<Name> tail) {
    super(first, tail);
  }
}
