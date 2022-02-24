package com.github.anilople.javalua.config;

/**
 * lua运行时的配置
 *
 * @author wxq
 */
public class Config {

  private static final Config INSTANCE = new Config();

  private final boolean print = getValue("lua.print", false);

  private Config() {}

  public static Config getInstance() {
    return INSTANCE;
  }

  static String getValue(String key, String defaultValue) {
    {
      // 系统属性
      String valueFromSystemProperty = System.getProperty(key);
      if (null != valueFromSystemProperty) {
        return valueFromSystemProperty;
      }
    }
    return defaultValue;
  }

  static boolean getValue(String key, boolean defaultValue) {
    String stringValue = getValue(key, String.valueOf(defaultValue));
    if ("true".equals(stringValue)) {
      return true;
    } else if ("false".equals(stringValue)) {
      return false;
    } else {
      throw new IllegalStateException("value cannot be '" + stringValue + "'");
    }
  }

  public boolean needPrint() {
    return this.print;
  }
}
