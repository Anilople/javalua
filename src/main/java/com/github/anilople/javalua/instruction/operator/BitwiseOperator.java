package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 */
public final class BitwiseOperator extends AbstractOperator<LuaValue, LuaValue, LuaInteger> {
  public static final BitwiseOperator LUA_OPBAND = new BitwiseOperator(7, "&", Bitwise::and);
  public static final BitwiseOperator LUA_OPBOR = new BitwiseOperator(8, "|", Bitwise::or);
  public static final BitwiseOperator LUA_OPBXOR = new BitwiseOperator(9, "~", Bitwise::xor);
  public static final BitwiseOperator LUA_OPSHL = new BitwiseOperator(10, "<<", Bitwise::shiftLeft);
  public static final BitwiseOperator LUA_OPSHR = new BitwiseOperator(11, ">>", Bitwise::shiftRight);
  public static final BitwiseOperator LUA_OPBNOT = new BitwiseOperator(13, "~", Bitwise::negate);
  private BitwiseOperator(int enumCount, String content,
      BiFunction<LuaValue, LuaValue, LuaInteger> function) {
    super(enumCount, content, function);
  }
}
