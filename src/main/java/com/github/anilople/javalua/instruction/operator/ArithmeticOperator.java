package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L196-L210">lua.h</a>
 */
public final class ArithmeticOperator extends AbstractOperator<LuaValue, LuaValue, LuaValue> {
  public static final ArithmeticOperator LUA_OPADD =
      new ArithmeticOperator(0, "+", Arithmetic::add);
  public static final ArithmeticOperator LUA_OPSUB =
      new ArithmeticOperator(1, "-", Arithmetic::sub);
  public static final ArithmeticOperator LUA_OPMUL =
      new ArithmeticOperator(2, "*", Arithmetic::multiply);
  public static final ArithmeticOperator LUA_OPMOD =
      new ArithmeticOperator(3, "%", Arithmetic::module);
  public static final ArithmeticOperator LUA_OPPOW =
      new ArithmeticOperator(4, "^", Arithmetic::power);
  public static final ArithmeticOperator LUA_OPDIV =
      new ArithmeticOperator(5, "/", Arithmetic::division);
  public static final ArithmeticOperator LUA_OPIDIV =
      new ArithmeticOperator(6, "//", Arithmetic::floorDivision);
  /**
   * unary minus
   */
  public static final ArithmeticOperator LUA_OPUNM =
      new ArithmeticOperator(12, "-", Arithmetic::unaryMinus);

  private ArithmeticOperator(
      int enumCount, String content, BiFunction<LuaValue, LuaValue, LuaValue> function) {
    super(enumCount, content, function);
  }
}