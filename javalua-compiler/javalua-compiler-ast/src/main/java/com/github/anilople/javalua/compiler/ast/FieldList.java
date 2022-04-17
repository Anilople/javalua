package com.github.anilople.javalua.compiler.ast;

import java.util.List;

/**
 * fieldlist ::= field {fieldsep field} [fieldsep]
 *
 * @author wxq
 */
public class FieldList extends XxxList<Field> {
  public FieldList(Field first, List<Field> tail) {
    super(first, tail);
  }
}
