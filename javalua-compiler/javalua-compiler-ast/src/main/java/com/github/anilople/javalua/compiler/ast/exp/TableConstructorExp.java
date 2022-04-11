package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.util.Optional;
import lombok.Getter;

/**
 * tableconstructor ::= ‘{’ [fieldlist] ‘}’
 *
 * @author wxq
 */
@Getter
public class TableConstructorExp extends AbstractExp {
  private final Optional<FieldList> optionalFieldList;

  public TableConstructorExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
    this.optionalFieldList = Optional.empty();
  }

  public TableConstructorExp(LuaAstLocation luaAstLocation, FieldList fieldList) {
    super(luaAstLocation);
    this.optionalFieldList = Optional.of(fieldList);
  }
}
