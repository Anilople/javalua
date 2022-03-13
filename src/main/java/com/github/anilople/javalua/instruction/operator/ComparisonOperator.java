package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 */
public final class ComparisonOperator extends AbstractOperator<LuaValue, LuaValue, LuaBoolean> {
  public static final ComparisonOperator LUA_OPEQ =
      new ComparisonOperator(0, "==", Comparison::equals, "__eq");
  public static final ComparisonOperator LUA_OPLT =
      new ComparisonOperator(1, "<", Comparison::lessThan, "__lt");
  public static final ComparisonOperator LUA_OPLE =
      new ComparisonOperator(2, "<=", Comparison::lessThanOrEquals, "__le");

  private ComparisonOperator(
      int enumCount, String content, BiFunction<LuaValue, LuaValue, LuaBoolean> function, String metaMethodName) {
    super(enumCount, content, function, metaMethodName);
  }
}
