package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wxq
 */
public class ResourceReadUtils {

  /**
   * 读取 src/main/resources 或者 src/test/resources 下的文件
   *
   * @return 二进制
   */
  public static byte[] readBytes(String resourcePath) {
    InputStream inputStream =
        ResourceReadUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    assert inputStream != null;
    try {
      return inputStream.readAllBytes();
    } catch (IOException e) {
      throw new IllegalStateException("cannot read resources " + resourcePath, e);
    }
  }

  /**
   * 读取 src/main/resources 或者 src/test/resources 下的文件
   *
   * @return 字符串
   */
  public static String readString(String resourcePath) {
    byte[] bytes = readBytes(resourcePath);
    return new String(bytes, StandardCharsets.UTF_8);
  }
}
