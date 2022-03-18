package util;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.stdlib.print;
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
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    LuaVM luaVM = LuaVM.create(1, new Prototype());
    print.registerTo(luaVM, new PrintStream(byteArrayOutputStream));
    luaVM.load(luaResource.getLuacOut(), luaResource.getLuaFilePath(), "b");
    luaVM.call(0, 0);

    // 返回 stdout
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }
}
