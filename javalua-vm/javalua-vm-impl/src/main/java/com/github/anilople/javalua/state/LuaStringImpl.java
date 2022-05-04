package com.github.anilople.javalua.state;

import java.util.Objects;

/**
 * @author wxq
 */
public class LuaStringImpl implements LuaString {

  private final String javaValue;

  public LuaStringImpl() {
    throw new UnsupportedOperationException();
  }

  public LuaStringImpl(String javaValue) {
    this.javaValue = javaValue;
  }

  @Override
  public String getJavaValue() {
    return this.javaValue;
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
    return Objects.equals(javaValue, luaString.getJavaValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(javaValue);
  }

  @Override
  public String toString() {
    return this.javaValue;
  }

  public LuaBoolean lessThan(LuaString luaString) {
    int compareResult = this.javaValue.compareTo(luaString.getJavaValue());
    return LuaValue.of(compareResult < 0);
  }

  public LuaInteger length() {
    var len = this.javaValue.length();
    return LuaInteger.newLuaInteger(len);
  }

  public LuaNumber toLuaNumber() {
    double value = Double.parseDouble(this.javaValue);
    return LuaNumber.newLuaNumber(value);
  }

  public LuaString concat(LuaString luaString) {
    String value = this.javaValue + luaString.getJavaValue();
    return LuaString.newLuaString(value);
  }

  @Override
  public LuaString toLuaString() {
    return this;
  }
}
