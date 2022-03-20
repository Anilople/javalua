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
      this.luacOut =
          ResourceReadUtils.readBytes(LuaResourceUtils.resolveOutFilename(this.luaFilePath));
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

  interface ch10 {
    LuaResource function_call_nested_case1 = new LuaResource("ch10/function_call_nested_case1.lua");
    LuaResource page_185_upvalue = new LuaResource("ch10/page_185_upvalue.lua");
    LuaResource page_186_upvalue_nested = new LuaResource("ch10/page_186_upvalue_nested.lua");
    LuaResource page_188_global_variable = new LuaResource("ch10/page_188_global_variable.lua");
    LuaResource page_195_GETUPVAL = new LuaResource("ch10/page_195_GETUPVAL.lua");
    LuaResource page_197_SETUPVAL = new LuaResource("ch10/page_197_SETUPVAL.lua");
    LuaResource page_198_GETTABUP = new LuaResource("ch10/page_198_GETTABUP.lua");
    LuaResource page_199_SETTABUP = new LuaResource("ch10/page_199_SETTABUP.lua");
    LuaResource page_200_JMP = new LuaResource("ch10/page_200_JMP.lua");
    LuaResource page_202_test = new LuaResource("ch10/page_202_test.lua");
    LuaResource page_202_test_simpler_case1 =
        new LuaResource("ch10/page_202_test_simpler_case1.lua");
    LuaResource upvalue_add_case1 = new LuaResource("ch10/upvalue_add_case1.lua");
    LuaResource upvalue_add_case2 = new LuaResource("ch10/upvalue_add_case2.lua");
  }

  interface ch11 {
    LuaResource getmetatable_nil = new LuaResource("ch11/getmetatable_nil.lua");
    LuaResource getmetatable_string = new LuaResource("ch11/getmetatable_string.lua");
    LuaResource get_from_array = new LuaResource("ch11/get_from_array.lua");
    LuaResource get_from_array_vararg = new LuaResource("ch11/get_from_array_vararg.lua");
    LuaResource page_206_debug_setmetatable =
        new LuaResource("ch11/page_206_debug_setmetatable.lua");
    LuaResource page_206_getmetatable = new LuaResource("ch11/page_206_getmetatable.lua");
    LuaResource page_207_metatable_add = new LuaResource("ch11/page_207_metatable_add.lua");
    LuaResource setmetatable_case1 = new LuaResource("ch11/setmetatable_case1.lua");
    LuaResource sub = new LuaResource("ch11/sub.lua");
    LuaResource TAILCALL_case1 = new LuaResource("ch11/TAILCALL_case1.lua");
    LuaResource __eq_always_true = new LuaResource("ch11/__eq_always_true.lua");
    LuaResource __eq_not_call = new LuaResource("ch11/__eq_not_call.lua");
    LuaResource __len_fixed = new LuaResource("ch11/__len_fixed.lua");
    LuaResource __sub_print_self = new LuaResource("ch11/__sub_print_self.lua");
  }
}
