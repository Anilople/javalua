package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 比较运算符，也叫 关系（Relational）运算符
 *
 * page 84
 */
public class Comparison {

  /**
   * 参数从类型上，是否支持{@link #equals(LuaValue, LuaValue)}运算，如果不支持，需要走元方法
   */
  public static boolean isEqualsTypeMatch(LuaValue a, LuaValue b) {
    // 当2个操作数是不同的表时，才会执行元方法
    if (!LuaType.LUA_TTABLE.equals(a.type())) {
      return true;
    }
    if (!LuaType.LUA_TTABLE.equals(b.type())) {
      return true;
    }
    // 2个都是table，必须是不同的table，才会执行元方法
    // 如果table相同，应该返回true
    return a.equals(b);
  }

  /**
   * 只有当2个操作数在Lua语言层面具有相同类型时，等于运算才有可能返回true
   */
  public static LuaBoolean equals(LuaValue a, LuaValue b) {
    var compareResult = a.equals(b);
    return compareResult ? LuaValue.TRUE : LuaValue.FALSE;
  }

  public static LuaBoolean notEquals(LuaValue a, LuaValue b) {
    var luaBoolean = equals(a, b);
    return Logical.not(luaBoolean);
  }

  static boolean isLessThanTypeMatch(LuaValue luaValue) {
    if (LuaType.LUA_TNUMBER.equals(luaValue.type())) {
      return true;
    }
    if (LuaType.LUA_TSTRING.equals(luaValue.type())) {
      return true;
    }
    return false;
  }

  /**
   * 参数从类型上，是否支持{@link #lessThan(LuaValue, LuaValue)}运算，如果不支持，需要走元方法
   */
  public static boolean isLessThanTypeMatch(LuaValue a, LuaValue b) {
    if (isLessThanTypeMatch(a)) {
      return true;
    }
    if (isLessThanTypeMatch(b)) {
      return true;
    }
    return false;
  }

  /**
   * 仅对数字和字符串类型有意义
   */
  public static LuaBoolean lessThan(LuaValue a, LuaValue b) {
    if (a instanceof LuaString) {
      if (b instanceof LuaString) {
        return ((LuaString) a).lessThan((LuaString) b);
      } else {
        return LuaValue.FALSE;
      }
    } else if (a instanceof LuaInteger) {
      if (b instanceof LuaInteger) {
        var result = ((LuaInteger) a).lessThen((LuaInteger) b);
        return LuaValue.of(result);
      } else {
        return LuaValue.FALSE;
      }
    } else if (a instanceof LuaNumber) {
      if (b instanceof LuaNumber) {
        var result = ((LuaNumber) a).lessThen((LuaNumber) b);
        return LuaValue.of(result);
      } else {
        return LuaValue.FALSE;
      }
    } else {
      throw new UnsupportedOperationException("a " + a + " b " + b);
    }
  }

  /**
   * 参数从类型上，是否支持{@link #lessThanOrEquals(LuaValue, LuaValue)}运算，如果不支持，需要走元方法
   */
  public static boolean isLessThanOrEqualsTypeMatch(LuaValue a, LuaValue b) {
    return isLessThanTypeMatch(a, b);
  }

  public static LuaBoolean lessThanOrEquals(LuaValue a, LuaValue b) {
    var lt = lessThan(a, b);
    var eq = equals(a, b);
    return Logical.or(lt, eq);
  }

  public static LuaBoolean biggerThan(LuaValue a, LuaValue b) {
    var lte = lessThanOrEquals(a, b);
    return Logical.notLuaBoolean(lte);
  }

  public static LuaBoolean biggerThanOrEquals(LuaValue a, LuaValue b) {
    var lt = lessThan(a, b);
    return Logical.notLuaBoolean(lt);
  }
}
