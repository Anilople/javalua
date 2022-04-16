package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.NameList;
import java.util.Optional;
import lombok.Data;

/**
 * 局部变量声明语句
 *
 * local namelist [‘=’ explist]
 *
 * @author wxq
 */
@Data
public class LocalVarDeclStat implements Stat {
  private final NameList namelist;
  /**
   * [‘=’ explist]
   */
  private final Optional<ExpList> explist;
}
