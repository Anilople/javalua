package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.stat.Stat;
import java.io.PrintStream;
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

  @Override
  public void toLuaCode(PrintStream printStream) {
    if (this.optionalRetstat.isPresent()) {
      for (Stat stat : this.statList) {
        // 1个stat 1行
        stat.toLuaCode(printStream);
        this.toLuaCodeNewline(printStream);
      }
      Retstat retstat = this.optionalRetstat.get();
      retstat.toLuaCode(printStream);
    } else {
      // 注意换行
      for (int i = 0; i < this.statList.size() - 1; i++) {
        Stat stat = this.statList.get(i);
        stat.toLuaCode(printStream);
        this.toLuaCodeNewline(printStream);
      }
      // 最后1个stat不需要换行
      int lastIndex = this.statList.size() - 1;
      if (lastIndex >= 0) {
        Stat stat = this.statList.get(lastIndex);
        stat.toLuaCode(printStream);
      }
    }
  }
}
