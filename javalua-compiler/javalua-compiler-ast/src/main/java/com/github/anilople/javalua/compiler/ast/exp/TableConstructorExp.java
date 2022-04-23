package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;
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

  public TableConstructorExp(LuaAstLocation luaAstLocation, Optional<FieldList> optionalFieldList) {
    super(luaAstLocation);
    this.optionalFieldList = optionalFieldList;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print('{');
    if (this.optionalFieldList.isPresent()) {
      FieldList fieldList = this.optionalFieldList.get();
      fieldList.toLuaCode(printStream);
    }
    printStream.print('}');
  }
}
