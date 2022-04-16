package com.github.anilople.javalua.util;

/**
 * @author wxq
 */
public class LuaConvertUtils {

  public static Return2<Boolean, Double> convertToLuaNumber(String content) {
    try {
      Double value = Double.parseDouble(content);
      return new Return2<>(true, value);
    } catch (NumberFormatException e) {
      return new Return2<>(false, null);
    }
  }

  public static Return2<Boolean, Long> convertToLuaInteger(String content) {
    try {
      Long value = Long.parseLong(content);
      return new Return2<>(true, value);
    } catch (NumberFormatException e) {
      return new Return2<>(false, null);
    }
  }
}
