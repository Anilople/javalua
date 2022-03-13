package com.github.anilople.javalua.instruction.operator;

import static com.github.anilople.javalua.constant.LuaConstants.MetaMethod.Bitwise.*;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 */
public final class BitwiseOperator extends AbstractOperator<LuaValue, LuaValue, LuaInteger> {
  public static final BitwiseOperator LUA_OPBAND = new BitwiseOperator(7, "&", Bitwise::and, AND);
  public static final BitwiseOperator LUA_OPBOR = new BitwiseOperator(8, "|", Bitwise::or, OR);
  public static final BitwiseOperator LUA_OPBXOR = new BitwiseOperator(9, "~", Bitwise::xor, XOR);
  public static final BitwiseOperator LUA_OPSHL =
      new BitwiseOperator(10, "<<", Bitwise::shiftLeft, SHL);
  public static final BitwiseOperator LUA_OPSHR =
      new BitwiseOperator(11, ">>", Bitwise::shiftRight, SHR);
  public static final BitwiseOperator LUA_OPBNOT =
      new BitwiseOperator(13, "~", Bitwise::negate, NOT);

  private BitwiseOperator(
      int enumCount,
      String content,
      BiFunction<LuaValue, LuaValue, LuaInteger> function,
      LuaString metaMethodName) {
    super(enumCount, content, function, metaMethodName);
  }
}
