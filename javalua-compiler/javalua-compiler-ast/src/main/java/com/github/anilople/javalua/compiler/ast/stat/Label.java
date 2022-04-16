package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;
import lombok.Data;

/**
 * label ::= ‘::’ Name ‘::’
 *
 * @author wxq
 */
@Data
public class Label implements Stat {
  private final Name name;
}
