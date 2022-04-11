package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.Funcname;
import lombok.Data;

/**
 * local function Name funcbody
 *
 * @see FunctionDefineStat
 * @author wxq
 */
@Data
public class LocalFunctionDefineStat implements Stat {
  private final Funcname funcname;
  private final FuncBody funcbody;
}
