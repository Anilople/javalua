package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import java.util.Objects;

/**
 * @author wxq
 */
public class LuaStringImpl implements LuaString {

  private String value;

  @Override
  public void init(String javaValue) {
    this.value = javaValue;
  }

  @Override
  public String getJavaValue() {
    return this.value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TSTRING;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LuaString luaString = (LuaString) o;
    return Objects.equals(value, luaString.getJavaValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "\"" + this.value + "\"";
  }

  public LuaBoolean lessThan(LuaString luaString) {
    int compareResult = this.value.compareTo(luaString.getJavaValue());
    return LuaValue.of(compareResult < 0);
  }

  public LuaInteger length() {
    var len = this.value.length();
    return LuaInteger.newLuaInteger(len);
  }

  public LuaNumber toLuaNumber() {
    double value = Double.parseDouble(this.value);
    return LuaNumber.newLuaNumber(value);
  }

  public LuaString concat(LuaString luaString) {
    String value = this.value + luaString.getJavaValue();
    return LuaString.newLuaString(value);
  }
}
