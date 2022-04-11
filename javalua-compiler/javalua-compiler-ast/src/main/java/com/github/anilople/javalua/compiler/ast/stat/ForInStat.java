package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.NameList;
import lombok.Data;

/**
 * for namelist in explist do block end
 *
 * 通用for循环
 *
 * @author wxq
 */
@Data
public class ForInStat implements Stat {
  private final int lineOfDo;
  private final NameList namelist;
  private final ExpList explist;
  private final Block block;
}
