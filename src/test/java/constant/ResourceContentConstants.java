package constant;

import util.ResourceReadUtils;

/**
 * little endian 小端
 *
 * 汇总 src/test/resources 下的资源
 *
 * @author wxq
 */
public interface ResourceContentConstants {

  interface ch02 {
    byte[] helloWorldLuac53Out = ResourceReadUtils.readBytes("ch02/hello_world.luac53.out");
    byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");
  }

  interface ch06 {
    byte[] sumLuac53Out = ResourceReadUtils.readBytes("ch06/sum.luac53.out");
  }

  interface ch07 {
    byte[] newTableLuac53Out = ResourceReadUtils.readBytes("ch07/new_table.luac53.out");
    byte[] testLuac53Out = ResourceReadUtils.readBytes("ch07/test.luac53.out");
  }

  interface ch08 {
    byte[] closure = ResourceReadUtils.readBytes("ch08/closure.luac53.out");
  }
}
