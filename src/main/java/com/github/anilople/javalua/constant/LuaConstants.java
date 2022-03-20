package com.github.anilople.javalua.constant;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h">lua.h</a>
 */
public interface LuaConstants {

  /**
   * 栈的最小大小
   */
  int LUA_MIN_STACK = 20;

  int LUA_I_MAX_STACK = 1000000;
  /**
   * 注册表的索引。
   * 伪索引 Pseudo-indices
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L42">LUA_REGISTRYINDEX	(-LUAI_MAXSTACK - 1000)</a>
   */
  int LUA_REGISTRY_INDEX = -LUA_I_MAX_STACK - 1000;

  /**
   * 全局环境在注册表里的索引
   */
  LuaInteger LUA_RIDX_GLOBALS = LuaValue.of(2L);

  /**
   * 元方法
   */
  interface MetaMethod {
    interface Arithmetic {
      LuaString ADD = LuaValue.of("__add");
      LuaString SUB = LuaValue.of("__sub");
      LuaString MUL = LuaValue.of("__mul");
      LuaString MOD = LuaValue.of("__mod");
      LuaString POW = LuaValue.of("__pow");
      LuaString DIV = LuaValue.of("__div");
      LuaString IDIV = LuaValue.of("__idiv");
      LuaString UNM = LuaValue.of("__unm");
    }

    interface Bitwise {
      LuaString AND = LuaValue.of("__band");
      LuaString OR = LuaValue.of("__bor");
      LuaString XOR = LuaValue.of("__bxor");
      LuaString SHL = LuaValue.of("__shl");
      LuaString SHR = LuaValue.of("__shr");
      LuaString NOT = LuaValue.of("__bnot");
    }

    interface Comparison {
      LuaString EQ = LuaValue.of("__eq");
      LuaString LT = LuaValue.of("__lt");
      LuaString LE = LuaValue.of("__le");
    }

    LuaString LEN = LuaValue.of("__len");
    LuaString CONCAT = LuaValue.of("__concat");
    LuaString INDEX = LuaValue.of("__index");
    LuaString NEWINDEX = LuaValue.of("__newindex");
    LuaString CALL = LuaValue.of("__call");
  }
}
