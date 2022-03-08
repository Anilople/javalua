package constant;

import lombok.Getter;
import util.LuaResourceUtils;
import util.ResourceReadUtils;

/**
 * little endian 小端
 *
 * 汇总 src/test/resources 下的资源
 *
 * 如果没有特殊表示，都是luac53的编译输出
 *
 * @author wxq
 */
public interface ResourceContentConstants {

  @Getter
  class LuaResource {
    private final String luaFilePath;
    private final byte[] luacOut;

    public LuaResource(String luaFilePath) {
      this.luaFilePath = luaFilePath;
      this.luacOut = ResourceReadUtils.readBytes(LuaResourceUtils.resolveOutFilename(this.luaFilePath));
    }
  }

  interface ch02 {
    LuaResource hello_world = new LuaResource("ch02/hello_world.lua");
    byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");
  }

  interface ch06 {
    LuaResource sum = new LuaResource("ch06/sum.lua");
  }

  interface ch07 {
    LuaResource get_table = new LuaResource("ch07/get_table.lua");
    LuaResource new_table = new LuaResource("ch07/new_table.lua");
    LuaResource set_list = new LuaResource("ch07/set_list.lua");
    LuaResource test = new LuaResource("ch07/test.lua");
  }

  interface ch08 {
    LuaResource call = new LuaResource("ch08/call.lua");
    LuaResource call_operand_zero = new LuaResource("ch08/call_operand_zero.lua");
    LuaResource closure = new LuaResource("ch08/closure.lua");
    LuaResource max_case1 = new LuaResource("ch08/max_case1.lua");
    LuaResource max_case2 = new LuaResource("ch08/max_case2.lua");
    LuaResource max_with_vararg_case1 = new LuaResource("ch08/max_with_vararg_case1.lua");
    LuaResource max_with_vararg_case2 = new LuaResource("ch08/max_with_vararg_case2.lua");
    LuaResource _return = new LuaResource("ch08/return.lua");
    LuaResource test = new LuaResource("ch08/test.lua");
    LuaResource vararg_case1 = new LuaResource("ch08/vararg_case1.lua");
    LuaResource vararg_case2 = new LuaResource("ch08/vararg_case2.lua");
  }
}
