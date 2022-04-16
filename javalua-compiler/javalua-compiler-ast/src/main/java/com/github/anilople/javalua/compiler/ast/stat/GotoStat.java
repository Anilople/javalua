package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class GotoStat implements Stat {
  private final Name name;
}
