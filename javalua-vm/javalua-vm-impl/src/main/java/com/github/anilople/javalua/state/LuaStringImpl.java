package com.github.anilople.javalua.state;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
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
    if (null == javaValue) {
      throw new IllegalArgumentException("argument cannot be Java's null");
    }
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

  @Override
  public LuaString toLuaString() {
    return this;
  }

  @Override
  public boolean canConvertToLuaNumber() {
    // todo, 引入cache
    try {
      Double.parseDouble(this.javaValue);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  public LuaNumber toLuaNumber() {
    if (!this.canConvertToLuaNumber()) {
      throw new TypeConversionRuntimeException(this, LuaNumber.class);
    }
    // todo, 引入cache
    double value = Double.parseDouble(this.javaValue);
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public boolean canConvertToLuaInteger() {
    if (!this.canConvertToLuaNumber()) {
      return false;
    }
    LuaNumber luaNumber = this.toLuaNumber();
    return luaNumber.canConvertToLuaInteger();
  }

  @Override
  public LuaInteger toLuaInteger() {
    if (!this.canConvertToLuaInteger()) {
      throw new TypeConversionRuntimeException(this, LuaInteger.class);
    }
    LuaNumber luaNumber = this.toLuaNumber();
    return luaNumber.toLuaInteger();
  }

  @Override
  public LuaBoolean lessThan(LuaString luaString) {
    int compareResult = this.javaValue.compareTo(luaString.getJavaValue());
    return LuaValue.of(compareResult < 0);
  }

  @Override
  public LuaInteger length() {
    var len = this.javaValue.length();
    return LuaInteger.newLuaInteger(len);
  }

  @Override
  public LuaString concat(LuaString luaString) {
    String value = this.javaValue + luaString.getJavaValue();
    return LuaString.newLuaString(value);
  }
}
