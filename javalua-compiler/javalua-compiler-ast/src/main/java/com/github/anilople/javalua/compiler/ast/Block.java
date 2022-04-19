package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.stat.Stat;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

/**
 *
 * block ::= {stat} [retstat]
 *
 * @author wxq
 */
@Getter
public class Block extends AbstractLuaAst {

  private final List<Stat> statList;
  private final Optional<Retstat> optionalRetstat;

  public Block(
      LuaAstLocation luaAstLocation, List<Stat> statList, Optional<Retstat> optionalRetstat) {
    super(luaAstLocation);
    this.statList = statList;
    this.optionalRetstat = optionalRetstat;
  }
}
