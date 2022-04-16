package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.util.Optional;

/**
 * 数值for循环语句
 *
 * for Name ‘=’ exp ‘,’ exp [‘,’ exp] do block end
 *
 * 要把关键字for和do所在的行号记录下来，代码生成阶段需要使用
 *
 * 样例代码如下
 * <pre>
 *   {@code
 *    for var=initExp,limitExp,stepExp do
 *      -- block的内容
 *    end
 *   }
 * </pre>
 *
 * 相当于Java的代码（stepExp > 0时）
 * <pre>
 *   {@code
 *    for (int var=initExp; var <= limitExp; var += stepExp) {
 *      -- block的内容
 *    }
 *   }
 * </pre>
 * 或者
 * 相当于Java的代码（stepExp < 0时）
 * <pre>
 *   {@code
 *    for (int var=initExp; var >= limitExp; var += stepExp) {
 *      -- block的内容
 *    }
 *   }
 * </pre>
 * @author wxq
 */
public class ForNumStat extends AbstractStat {

  /**
   * do 关键字所在位置
   */
  private final LuaAstLocation locationOfDo;

  private final Name name;
  private final Exp initExp;
  private final Exp limitExp;
  /**
   * 不存在时，使用数值 1
   */
  private final Optional<Exp> stepExp;

  private final Block block;

  public ForNumStat(
      LuaAstLocation locationOfFor,
      LuaAstLocation locationOfDo,
      Name name,
      Exp initExp,
      Exp limitExp,
      Optional<Exp> stepExp,
      Block block) {
    super(locationOfFor);
    this.locationOfDo = locationOfDo;
    this.name = name;
    this.initExp = initExp;
    this.limitExp = limitExp;
    this.stepExp = stepExp;
    this.block = block;
  }
}
