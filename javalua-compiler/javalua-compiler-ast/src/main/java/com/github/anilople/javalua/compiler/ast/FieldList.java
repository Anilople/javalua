package com.github.anilople.javalua.compiler.ast;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * fieldlist ::= field {fieldsep field} [fieldsep]
 *
 * @author wxq
 */
public class FieldList extends AbstractLuaAst {
  private final Field field;
  /**
   * {fieldsep field}
   */
  private final List<Entry<FieldSep, Field>> list;
  private final Optional<FieldSep> optionalFieldsep;

  public FieldList(LuaAstLocation luaAstLocation,
      Field field,
      List<Entry<FieldSep, Field>> list,
      Optional<FieldSep> optionalFieldsep) {
    super(luaAstLocation);
    this.field = field;
    this.list = list;
    this.optionalFieldsep = optionalFieldsep;
  }
}
