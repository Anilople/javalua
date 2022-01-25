package com.github.anilople.javalua;

import java.io.IOException;

/**
 * little endian 小端
 *
 * @author wxq
 */
public interface ResourceContentConstants {

  interface ch02 {
    byte[] helloWorldLuac53Out = ResourceReadUtils.readBytes("ch02/hello_world.luac53.out");
    byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");
  }

}
