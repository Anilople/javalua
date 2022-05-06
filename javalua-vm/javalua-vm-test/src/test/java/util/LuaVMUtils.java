package util;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.Prototype;
import io.LuaTestResource;
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
   * @param luaTestResource src/test/resources 下的lua资源
   * @return stdout 标准输出
   */
  public static String run(LuaTestResource luaTestResource) {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    LuaVM luaVM = LuaVM.newLuaVM(new PrintStream(byteArrayOutputStream), 1, new Prototype());

    luaVM.load(luaTestResource.getLuacOut(), luaTestResource.getLuaFilePath(), "b");

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
