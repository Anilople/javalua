package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.exception.TypeConversionRuntimeException;

public interface LuaValue {
  LuaType type();

  LuaNil NIL = LuaNil.INSTANCE;
  LuaBoolean TRUE = LuaBoolean.TRUE;
  LuaBoolean FALSE = LuaBoolean.FALSE;

  static LuaBoolean of(boolean value) {
    return value ? LuaValue.TRUE : LuaValue.FALSE;
  }

  static LuaInteger of(long value) {
    return LuaInteger.newLuaInteger(value);
  }

  static LuaNumber of(double value) {
    return LuaNumber.newLuaNumber(value);
  }

  static LuaString of(String value) {
    return LuaString.newLuaString(value);
  }

  /**
   * @return true如果表示的是lua里的nil
   */
  default boolean isLuaNil() {
    if (this instanceof LuaNil) {
      if (this.equals(NIL)) {
        return true;
      } else {
        throw new IllegalStateException("type is " + LuaNil.class + " but value isn't " + NIL);
      }
    }
    return false;
  }

  default boolean isLuaTrue() {
    if (this instanceof LuaBoolean) {
      return this.equals(TRUE);
    }
    return false;
  }

  default boolean isLuaFalse() {
    return !this.isLuaTrue();
  }

  default LuaBoolean toLuaBoolean() {
    if (this.isLuaNil()) {
      return LuaBoolean.FALSE;
    }
    if (this instanceof LuaBoolean) {
      return (LuaBoolean) this;
    }
    return LuaBoolean.TRUE;
  }

  LuaString toLuaString();

  /**
   *
   * @return true如果可以转成 {@link LuaNumber}
   */
  default boolean canConvertToLuaNumber() {
    return false;
  }

  /**
   * 使用之前需要先调用{@link #canConvertToLuaNumber()}进行判断
   */
  default LuaNumber toLuaNumber() {
    throw new TypeConversionRuntimeException(this, LuaNumber.class);
  }

  /**
   *
   * @return true如果可以转成 {@link LuaInteger}
   */
  default boolean canConvertToLuaInteger() {
    return false;
  }

  /**
   * 使用之前需要先调用{@link #canConvertToLuaInteger()}进行判断
   */
  default LuaInteger toLuaInteger() {
    throw new TypeConversionRuntimeException(this, LuaInteger.class);
  }
}
