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
    byte[] call = ResourceReadUtils.readBytes("ch08/call.luac53.out");
    byte[] callOperandZero = ResourceReadUtils.readBytes("ch08/call_operand_zero.luac53.out");
    byte[] RETURN = ResourceReadUtils.readBytes("ch08/return.luac53.out");
    byte[] vararg = ResourceReadUtils.readBytes("ch08/vararg.luac53.out");
    byte[] maxCase1 = ResourceReadUtils.readBytes("ch08/max_case1.luac53.out");
    byte[] maxCase2 = ResourceReadUtils.readBytes("ch08/max_case2.luac53.out");
    byte[] maxWithVarargCase1 =
        ResourceReadUtils.readBytes("ch08/max_with_vararg_case1.luac53.out");
    byte[] maxWithVarargCase2 =
        ResourceReadUtils.readBytes("ch08/max_with_vararg_case2.luac53.out");
    byte[] maxWithVarargCase3 =
        ResourceReadUtils.readBytes("ch08/max_with_vararg_case3.luac53.out");
    byte[] test = ResourceReadUtils.readBytes("ch08/test.luac53.out");
  }
}
