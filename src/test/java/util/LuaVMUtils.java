package util;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.stdlib.error;
import com.github.anilople.javalua.api.stdlib.getmetatable;
import com.github.anilople.javalua.api.stdlib.ipairs;
import com.github.anilople.javalua.api.stdlib.next;
import com.github.anilople.javalua.api.stdlib.pairs;
import com.github.anilople.javalua.api.stdlib.pcall;
import com.github.anilople.javalua.api.stdlib.print;
import com.github.anilople.javalua.api.stdlib.setmetatable;
import com.github.anilople.javalua.chunk.Prototype;
import constant.ResourceContentConstants.LuaResource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wxq
 */
public class LuaVMUtils {

  /**
   * 运行lua的字节码，返回 stdout 标准输出
   *
   * @param luaResource src/test/resources 下的lua资源
   * @return stdout 标准输出
   */
  public static String run(LuaResource luaResource) {
    LuaVM luaVM = LuaVM.create(1, new Prototype());

    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    new print(new PrintStream(byteArrayOutputStream)).registerTo(luaVM);
    setmetatable.getInstance().registerTo(luaVM);
    getmetatable.getInstance().registerTo(luaVM);
    pairs.getInstance().registerTo(luaVM);
    ipairs.getInstance().registerTo(luaVM);
    next.getInstance().registerTo(luaVM);
    error.getInstance().registerTo(luaVM);
    pcall.getInstance().registerTo(luaVM);

    luaVM.load(luaResource.getLuacOut(), luaResource.getLuaFilePath(), "b");

    try {
      luaVM.call(0, 0);
    } catch (RuntimeException e) {
      String stdout = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
      throw new IllegalStateException("lua vm meet exception. stdout = '" + stdout + "'", e);
    }

    // 返回 stdout
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }
}
