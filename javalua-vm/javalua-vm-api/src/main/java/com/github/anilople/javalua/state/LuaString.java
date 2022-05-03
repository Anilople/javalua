package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import java.util.Objects;

public class LuaString implements LuaValue {

  public static final LuaString EMPTY = new LuaString("");

  public static Return2<LuaString, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaString) {
      return new Return2<>((LuaString) luaValue, true);
    }
    final LuaString luaString;
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      luaString = luaInteger.toLuaString();
    } else if (luaValue instanceof LuaNumber) {
      LuaNumber luaNumber = (LuaNumber) luaValue;
      luaString = luaNumber.toLuaString();
    } else {
      luaString = new LuaString("");
    }
    return new Return2<>(luaString, true);
  }

  private final String value;

  public LuaString(String value) {
    this.value = value;
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
    return Objects.equals(value, luaString.value);
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
    int compareResult = this.value.compareTo(luaString.value);
    return LuaValue.of(compareResult < 0);
  }

  public LuaInteger length() {
    var len = this.value.length();
    return new LuaInteger(len);
  }

  public LuaNumber toLuaNumber() {
    double value = Double.parseDouble(this.value);
    return new LuaNumber(value);
  }

  public LuaString concat(LuaString luaString) {
    String value = this.value + luaString.value;
    return new LuaString(value);
  }
}
