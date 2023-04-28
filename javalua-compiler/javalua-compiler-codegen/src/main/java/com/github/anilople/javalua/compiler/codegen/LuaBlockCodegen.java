package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.RetStat;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import java.util.List;

import static com.github.anilople.javalua.compiler.codegen.LuaStatCodegen.cgRetStat;
import static com.github.anilople.javalua.compiler.codegen.LuaStatCodegen.cgStat;

/**
 * @author wxq
 */
class LuaBlockCodegen {

  static void cgBlock(FunctionInfo functionInfo, Block node) {
    List<Stat> statList = node.getStatList();
    for (Stat stat : statList) {
      cgStat(functionInfo, stat);
    }

    if (node.getOptionalRetstat().isPresent()) {
      RetStat retstat = node.getOptionalRetstat().get();
      cgRetStat(functionInfo, retstat);
    }
  }

}
