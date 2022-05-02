package com.github.anilople.javalua.instruction.operator;

import static com.github.anilople.javalua.constant.LuaConstants.MetaMethod.Comparison.*;

import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 */
public final class ComparisonOperator extends AbstractOperator<LuaValue, LuaValue, LuaBoolean> {
  public static final ComparisonOperator LUA_OPEQ =
      new ComparisonOperator(0, "==", Comparison::equals, EQ);
  public static final ComparisonOperator LUA_OPLT =
      new ComparisonOperator(1, "<", Comparison::lessThan, LT);
  public static final ComparisonOperator LUA_OPLE =
      new ComparisonOperator(2, "<=", Comparison::lessThanOrEquals, LE);

  private ComparisonOperator(
      int enumCount,
      String content,
      BiFunction<LuaValue, LuaValue, LuaBoolean> function,
      LuaString metaMethodName) {
    super(enumCount, content, function, metaMethodName);
  }

  /**
   * @see com.github.anilople.javalua.state.LuaState#compare(int, int, ComparisonOperator)
   */
  @Override
  public boolean canApply(LuaValue a, LuaValue b) {
    throw new UnsupportedOperationException();
  }
}
