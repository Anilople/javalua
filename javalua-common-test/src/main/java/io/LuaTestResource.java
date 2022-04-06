package io;

import lombok.Getter;
import util.LuaTestResourceUtils;
import util.ResourceReadUtils;

/**
 * 测试的资源
 *
 * @author wxq
 */
@Getter
public class LuaTestResource {

  private final String luaFilePath;
  private final byte[] luacOut;

  public LuaTestResource(String luaFilePath) {
    this.luaFilePath = luaFilePath;
    this.luacOut =
        ResourceReadUtils.readBytes(LuaTestResourceUtils.resolveOutFilename(this.luaFilePath));
  }
}
