package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 比较运算符，也叫 关系（Relational）运算符
 */
public class Comparison {

  public static LuaBoolean equals(LuaValue a, LuaValue b) {
    var compareResult = a.equals(b);
    return compareResult ? LuaValue.TRUE : LuaValue.FALSE;
  }

  public static LuaBoolean notEquals(LuaValue a, LuaValue b) {
    var luaBoolean = equals(a, b);
    return Logical.not(luaBoolean);
  }

  public static LuaBoolean lessThan(LuaValue a, LuaValue b) {
    throw new UnsupportedOperationException();
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
