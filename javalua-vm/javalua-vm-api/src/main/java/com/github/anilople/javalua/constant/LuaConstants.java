package com.github.anilople.javalua.constant;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;

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
  LuaInteger LUA_RIDX_GLOBALS = LuaInteger.newLuaInteger(2L);

  /**
   * 元方法
   */
  interface MetaMethod {
    interface Arithmetic {
      LuaString ADD = LuaString.newLuaString("__add");
      LuaString SUB = LuaString.newLuaString("__sub");
      LuaString MUL = LuaString.newLuaString("__mul");
      LuaString MOD = LuaString.newLuaString("__mod");
      LuaString POW = LuaString.newLuaString("__pow");
      LuaString DIV = LuaString.newLuaString("__div");
      LuaString IDIV = LuaString.newLuaString("__idiv");
      LuaString UNM = LuaString.newLuaString("__unm");
    }

    interface Bitwise {
      LuaString AND = LuaString.newLuaString("__band");
      LuaString OR = LuaString.newLuaString("__bor");
      LuaString XOR = LuaString.newLuaString("__bxor");
      LuaString SHL = LuaString.newLuaString("__shl");
      LuaString SHR = LuaString.newLuaString("__shr");
      LuaString NOT = LuaString.newLuaString("__bnot");
    }

    interface Comparison {
      LuaString EQ = LuaString.newLuaString("__eq");
      LuaString LT = LuaString.newLuaString("__lt");
      LuaString LE = LuaString.newLuaString("__le");
    }

    LuaString LEN = LuaString.newLuaString("__len");
    LuaString CONCAT = LuaString.newLuaString("__concat");
    LuaString INDEX = LuaString.newLuaString("__index");
    LuaString NEWINDEX = LuaString.newLuaString("__newindex");
    LuaString CALL = LuaString.newLuaString("__call");
  }

  enum ThreadStatus {
    //    int LUA_OK = 0;
    //    int LUA_YIELD = 1;
    //    int LUA_ERRRUN = 2;
    //    int LUA_ERRSYNTAX = 3;
    //    int LUA_ERRMEM = 4;
    //    int LUA_ERRGCMM = 5;
    //    int LUA_ERRERR = 6;
    LUA_OK,
    LUA_YIELD,
    LUA_ERRRUN,
    LUA_ERRSYNTAX,
    LUA_ERRMEM,
    LUA_ERRGCMM,
    LUA_ERRERR,
  }
}
