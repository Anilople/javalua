package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

import static com.github.anilople.javalua.constant.LuaConstants.MetaMethod.Arithmetic.*;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L196-L210">lua.h</a>
 */
public final class ArithmeticOperator extends AbstractOperator<LuaValue, LuaValue, LuaValue> {
  public static final ArithmeticOperator LUA_OPADD =
      new ArithmeticOperator(0, "+", Arithmetic::add, ADD);
  public static final ArithmeticOperator LUA_OPSUB =
      new ArithmeticOperator(1, "-", Arithmetic::sub, SUB);
  public static final ArithmeticOperator LUA_OPMUL =
      new ArithmeticOperator(2, "*", Arithmetic::multiply, MUL);
  public static final ArithmeticOperator LUA_OPMOD =
      new ArithmeticOperator(3, "%", Arithmetic::module, MOD);
  public static final ArithmeticOperator LUA_OPPOW =
      new ArithmeticOperator(4, "^", Arithmetic::power, POW);
  public static final ArithmeticOperator LUA_OPDIV =
      new ArithmeticOperator(5, "/", Arithmetic::division, DIV);
  public static final ArithmeticOperator LUA_OPIDIV =
      new ArithmeticOperator(6, "//", Arithmetic::floorDivision, IDIV);
  /**
   * unary minus
   */
  public static final ArithmeticOperator LUA_OPUNM =
      new ArithmeticOperator(12, "-", Arithmetic::unaryMinus, UNM);

  private ArithmeticOperator(
      int enumCount, String content, BiFunction<LuaValue, LuaValue, LuaValue> function, LuaString metaMethodName) {
    super(enumCount, content, function, metaMethodName);
  }
}
