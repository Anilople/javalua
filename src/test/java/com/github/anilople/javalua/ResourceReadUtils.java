package com.github.anilople.javalua;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wxq
 */
public class ResourceReadUtils {

  public static byte[] readBytes(String resourcePath) throws IOException {
    InputStream inputStream =
        ResourceReadUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    assert inputStream != null;
    return inputStream.readAllBytes();
  }
}
