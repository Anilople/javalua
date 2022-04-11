package com.github.anilople.javalua.compiler.ast;

import com.github.anilople.javalua.compiler.ast.stat.Stat;
import java.util.List;
import java.util.Optional;
import lombok.Data;

/**
 *
 * block ::= {stat} [retstat]
 *
 * @author wxq
 */
@Data
public class Block {

  List<Stat> statList;
  Optional<Retstat> optionalRetstat;
}
