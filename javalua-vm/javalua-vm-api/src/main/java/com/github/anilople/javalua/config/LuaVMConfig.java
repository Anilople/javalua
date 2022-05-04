package com.github.anilople.javalua.config;

import com.github.anilople.javalua.util.SpiUtils;

/**
 * lua运行时的配置
 *
 * @author wxq
 */
public interface LuaVMConfig {

  static LuaVMConfig newLuaVMConfig() {
    return SpiUtils.loadOneInterfaceImpl(LuaVMConfig.class);
  }

  String getJavaString(String key, String defaultValue);

  boolean getJavaBoolean(String key, boolean defaultValue);

  default boolean needPrint() {
    return this.getJavaBoolean("lua.vm.print", false);
  }
}
