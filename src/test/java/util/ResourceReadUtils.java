package util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wxq
 */
public class ResourceReadUtils {

  /**
   * 读取 src/test/resources 下的文件
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
}
