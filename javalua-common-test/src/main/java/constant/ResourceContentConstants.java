package constant;

import io.LuaTestResource;
import util.ResourceReadUtils;

/**
 * little endian 小端
 *
 * 汇总 resources 下的资源
 *
 * 如果没有特殊表示，都是luac53的编译输出
 *
 * @author wxq
 */
public interface ResourceContentConstants {

  interface ch02 {
    LuaTestResource hello_world = new LuaTestResource("ch02/hello_world.lua");
    byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");
  }

  interface ch06 {
    LuaTestResource sum = new LuaTestResource("ch06/sum.lua");
  }

  interface ch07 {
    LuaTestResource get_table = new LuaTestResource("ch07/get_table.lua");
    LuaTestResource new_table = new LuaTestResource("ch07/new_table.lua");
    LuaTestResource set_list = new LuaTestResource("ch07/set_list.lua");
    LuaTestResource test = new LuaTestResource("ch07/test.lua");
  }

  interface ch08 {
    LuaTestResource call = new LuaTestResource("ch08/call.lua");
    LuaTestResource call_operand_zero = new LuaTestResource("ch08/call_operand_zero.lua");
    LuaTestResource closure = new LuaTestResource("ch08/closure.lua");
    LuaTestResource max_case1 = new LuaTestResource("ch08/max_case1.lua");
    LuaTestResource max_case2 = new LuaTestResource("ch08/max_case2.lua");
    LuaTestResource max_with_vararg_case1 = new LuaTestResource("ch08/max_with_vararg_case1.lua");
    LuaTestResource max_with_vararg_case2 = new LuaTestResource("ch08/max_with_vararg_case2.lua");
    LuaTestResource _return = new LuaTestResource("ch08/return.lua");
    LuaTestResource test = new LuaTestResource("ch08/test.lua");
    LuaTestResource vararg_case1 = new LuaTestResource("ch08/vararg_case1.lua");
    LuaTestResource vararg_case2 = new LuaTestResource("ch08/vararg_case2.lua");
  }

  interface ch10 {
    LuaTestResource function_call_nested_case1 = new LuaTestResource("ch10/function_call_nested_case1.lua");
    LuaTestResource page_185_upvalue = new LuaTestResource("ch10/page_185_upvalue.lua");
    LuaTestResource page_186_upvalue_nested = new LuaTestResource("ch10/page_186_upvalue_nested.lua");
    LuaTestResource page_188_global_variable = new LuaTestResource("ch10/page_188_global_variable.lua");
    LuaTestResource page_195_GETUPVAL = new LuaTestResource("ch10/page_195_GETUPVAL.lua");
    LuaTestResource page_197_SETUPVAL = new LuaTestResource("ch10/page_197_SETUPVAL.lua");
    LuaTestResource page_198_GETTABUP = new LuaTestResource("ch10/page_198_GETTABUP.lua");
    LuaTestResource page_199_SETTABUP = new LuaTestResource("ch10/page_199_SETTABUP.lua");
    LuaTestResource page_200_JMP = new LuaTestResource("ch10/page_200_JMP.lua");
    LuaTestResource page_202_test = new LuaTestResource("ch10/page_202_test.lua");
    LuaTestResource page_202_test_simpler_case1 =
        new LuaTestResource("ch10/page_202_test_simpler_case1.lua");
    LuaTestResource upvalue_add_case1 = new LuaTestResource("ch10/upvalue_add_case1.lua");
    LuaTestResource upvalue_add_case2 = new LuaTestResource("ch10/upvalue_add_case2.lua");
  }

  interface ch11 {
    LuaTestResource getmetatable_nil = new LuaTestResource("ch11/getmetatable_nil.lua");
    LuaTestResource getmetatable_string = new LuaTestResource("ch11/getmetatable_string.lua");
    LuaTestResource get_from_array = new LuaTestResource("ch11/get_from_array.lua");
    LuaTestResource get_from_array_vararg = new LuaTestResource("ch11/get_from_array_vararg.lua");
    LuaTestResource page_206_debug_setmetatable =
        new LuaTestResource("ch11/page_206_debug_setmetatable.lua");
    LuaTestResource page_206_getmetatable = new LuaTestResource("ch11/page_206_getmetatable.lua");
    LuaTestResource page_207_metatable_add = new LuaTestResource("ch11/page_207_metatable_add.lua");
    LuaTestResource setmetatable_case1 = new LuaTestResource("ch11/setmetatable_case1.lua");
    LuaTestResource sub = new LuaTestResource("ch11/sub.lua");
    LuaTestResource TAILCALL_case1 = new LuaTestResource("ch11/TAILCALL_case1.lua");
    LuaTestResource __eq_always_true = new LuaTestResource("ch11/__eq_always_true.lua");
    LuaTestResource __eq_not_call = new LuaTestResource("ch11/__eq_not_call.lua");
    LuaTestResource __len_fixed = new LuaTestResource("ch11/__len_fixed.lua");
    LuaTestResource __sub_print_self = new LuaTestResource("ch11/__sub_print_self.lua");
  }

  interface ch12 {
    LuaTestResource ipairs_array_1_element = new LuaTestResource("ch12/ipairs_array_1_element.lua");
    LuaTestResource ipairs_case1 = new LuaTestResource("ch12/ipairs_case1.lua");
    LuaTestResource ipairs_empty_array = new LuaTestResource("ch12/ipairs_empty_array.lua");
    LuaTestResource page_165_test_simpler_case1 =
        new LuaTestResource("ch12/page_165_test_simpler_case1.lua");
    LuaTestResource page_165_test_simpler_case2 =
        new LuaTestResource("ch12/page_165_test_simpler_case2.lua");
    LuaTestResource page_224_array_iterate = new LuaTestResource("ch12/page_224_array_iterate.lua");
    LuaTestResource page_224_for_in = new LuaTestResource("ch12/page_224_for_in.lua");
    LuaTestResource page_225_for_in_with_next = new LuaTestResource("ch12/page_225_for_in_with_next.lua");
    LuaTestResource page_226_for_in_with_next_simpler =
        new LuaTestResource("ch12/page_226_for_in_with_next_simpler.lua");
    LuaTestResource page_234_test = new LuaTestResource("ch12/page_234_test.lua");
    LuaTestResource pairs_array_1_element = new LuaTestResource("ch12/pairs_array_1_element.lua");
    LuaTestResource pairs_array_3_element = new LuaTestResource("ch12/pairs_array_3_element.lua");
    LuaTestResource pairs_empty_array = new LuaTestResource("ch12/pairs_empty_array.lua");
  }

  interface ch13 {
    LuaTestResource error_case1 = new LuaTestResource("ch13/error_case1.lua");
    LuaTestResource page_241_test = new LuaTestResource("ch13/page_241_test.lua");
    LuaTestResource pcall_error = new LuaTestResource("ch13/pcall_error.lua");
    LuaTestResource pcall_print = new LuaTestResource("ch13/pcall_print.lua");
  }
}
