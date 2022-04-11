package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.Funcname;
import lombok.Data;

/**
 * function funcname funcbody
 *
 * page 279
 *
 * @author wxq
 */
@Data
public class FunctionDefineStat implements Stat {
  private final Funcname funcname;
  private final FuncBody funcbody;
}
