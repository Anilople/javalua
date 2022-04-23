package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.NameList;
import java.io.PrintStream;

/**
 * for namelist in explist do block end
 *
 * 通用for循环
 *
 * @author wxq
 */
public class ForInStat extends AbstractStat {
  private final NameList namelist;
  private final ExpList explist;
  private final Block block;

  public ForInStat(LuaAstLocation luaAstLocation, NameList namelist, ExpList explist, Block block) {
    super(luaAstLocation);
    this.namelist = namelist;
    this.explist = explist;
    this.block = block;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("for ");
    this.namelist.toLuaCode(printStream);
    printStream.print(" in ");
    this.explist.toLuaCode(printStream);
    this.toLuaCodeDoBlockEnd(printStream, this.block::toLuaCode);
  }
}
