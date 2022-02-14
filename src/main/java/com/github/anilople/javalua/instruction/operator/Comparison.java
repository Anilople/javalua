package com.github.anilople.javalua.instruction.operator;

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

  /**
   * 仅对数字和字符串类型有意义
   */
  public static LuaBoolean lessThan(LuaValue a, LuaValue b) {
    if (a instanceof LuaString) {
      if (b instanceof LuaString) {
        var compareResult = ((LuaString) a).getValue().compareTo(((LuaString) b).getValue());
        return LuaValue.of(compareResult < 0);
      } else {
        return LuaValue.FALSE;
      }
    } else if (a instanceof LuaInteger) {
      if (b instanceof LuaInteger) {
        var result = ((LuaInteger) a).getValue() < ((LuaInteger) b).getValue();
        return LuaValue.of(result);
      } else {
        return LuaValue.FALSE;
      }
    } else if (a instanceof LuaNumber) {
      if (b instanceof LuaNumber) {
        var result = ((LuaNumber) a).getValue() < ((LuaNumber) b).getValue();
        return LuaValue.of(result);
      } else {
        return LuaValue.FALSE;
      }
    } else {
      throw new UnsupportedOperationException("a " + a + " b " + b);
    }
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
