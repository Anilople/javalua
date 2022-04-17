package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.NameList;
import java.util.Optional;
import lombok.Getter;

/**
 * 局部变量声明语句
 *
 * local namelist [‘=’ explist]
 *
 * @author wxq
 */
@Getter
public class LocalVarDeclStat extends AbstractStat {
  private final NameList namelist;
  /**
   * [‘=’ explist]
   */
  private final Optional<ExpList> optionalExpList;

  public LocalVarDeclStat(
      LuaAstLocation luaAstLocation, NameList namelist, Optional<ExpList> optionalExpList) {
    super(luaAstLocation);
    this.namelist = namelist;
    this.optionalExpList = optionalExpList;
  }
}
