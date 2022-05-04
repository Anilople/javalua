package com.github.anilople.javalua.config;

/**
 * @author wxq
 */
public class LuaVMConfigImpl implements LuaVMConfig {

  public LuaVMConfigImpl() {}

  @Override
  public String getJavaString(String key, String defaultValue) {
    {
      // 系统属性
      String valueFromSystemProperty = System.getProperty(key);
      if (null != valueFromSystemProperty) {
        return valueFromSystemProperty;
      }
    }
    return defaultValue;
  }

  @Override
  public boolean getJavaBoolean(String key, boolean defaultValue) {
    String stringValue = this.getJavaString(key, String.valueOf(defaultValue));
    if ("true".equals(stringValue)) {
      return true;
    } else if ("false".equals(stringValue)) {
      return false;
    } else {
      throw new IllegalStateException("value cannot be '" + stringValue + "'");
    }
  }

  @Override
  public boolean needPrint() {
    return this.getJavaBoolean("lua.vm.print", false);
  }
}
