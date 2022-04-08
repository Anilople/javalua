package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

  /**
   * 读取过的资源都会缓存到这里
   *
   * key是资源的路径，value是资源的内容
   */
  private static final Map<String, LuaTestResource> RESOURCE_REGISTRY = new ConcurrentHashMap<>();

  private final String luaFilePath;
  private final String luaCode;
  private final byte[] luacOut;

  private LuaTestResource(String luaFilePath) {
    this.luaFilePath = luaFilePath;
    this.luaCode = ResourceReadUtils.readString(luaFilePath);
    this.luacOut =
        ResourceReadUtils.readBytes(LuaTestResourceUtils.resolveOutFilename(this.luaFilePath));
  }

  private LuaTestResource(Path luaFile) {
    this.luaFilePath = luaFile.toAbsolutePath().toString();
    try {
      this.luaCode = Files.readString(luaFile);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    try {
      this.luacOut = Files.readAllBytes(LuaTestResourceUtils.resolveOutFilePath(luaFile));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static LuaTestResource resolve(String luaFilePath) {
    return RESOURCE_REGISTRY.computeIfAbsent(luaFilePath, LuaTestResource::new);
  }

  public static LuaTestResource resolve(Path luaFile) {
    return RESOURCE_REGISTRY.computeIfAbsent(
        luaFile.toAbsolutePath().toString(), key -> new LuaTestResource(luaFile));
  }
}
