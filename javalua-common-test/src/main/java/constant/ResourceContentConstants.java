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
    LuaTestResource hello_world = LuaTestResource.resolve("ch02/hello_world.lua");
    byte[] helloWorldLuac54Out = ResourceReadUtils.readBytes("ch02/hello_world.luac54.out");
  }

  interface ch06 {
    LuaTestResource sum = LuaTestResource.resolve("ch06/sum.lua");
  }

  interface ch07 {
    LuaTestResource get_table = LuaTestResource.resolve("ch07/get_table.lua");
    LuaTestResource new_table = LuaTestResource.resolve("ch07/new_table.lua");
    LuaTestResource set_list = LuaTestResource.resolve("ch07/set_list.lua");
    LuaTestResource test = LuaTestResource.resolve("ch07/test.lua");
  }

  interface ch08 {
    LuaTestResource call = LuaTestResource.resolve("ch08/call.lua");
    LuaTestResource call_operand_zero = LuaTestResource.resolve("ch08/call_operand_zero.lua");
    LuaTestResource closure = LuaTestResource.resolve("ch08/closure.lua");
    LuaTestResource max_case1 = LuaTestResource.resolve("ch08/max_case1.lua");
    LuaTestResource max_case2 = LuaTestResource.resolve("ch08/max_case2.lua");
    LuaTestResource max_with_vararg_case1 =
        LuaTestResource.resolve("ch08/max_with_vararg_case1.lua");
    LuaTestResource max_with_vararg_case2 =
        LuaTestResource.resolve("ch08/max_with_vararg_case2.lua");
    LuaTestResource _return = LuaTestResource.resolve("ch08/return.lua");
    LuaTestResource test = LuaTestResource.resolve("ch08/test.lua");
    LuaTestResource vararg_case1 = LuaTestResource.resolve("ch08/vararg_case1.lua");
    LuaTestResource vararg_case2 = LuaTestResource.resolve("ch08/vararg_case2.lua");
  }

  interface ch10 {
    LuaTestResource function_call_nested_case1 =
        LuaTestResource.resolve("ch10/function_call_nested_case1.lua");
    LuaTestResource page_185_upvalue = LuaTestResource.resolve("ch10/page_185_upvalue.lua");
    LuaTestResource page_186_upvalue_nested =
        LuaTestResource.resolve("ch10/page_186_upvalue_nested.lua");
    LuaTestResource page_188_global_variable =
        LuaTestResource.resolve("ch10/page_188_global_variable.lua");
    LuaTestResource page_195_GETUPVAL = LuaTestResource.resolve("ch10/page_195_GETUPVAL.lua");
    LuaTestResource page_197_SETUPVAL = LuaTestResource.resolve("ch10/page_197_SETUPVAL.lua");
    LuaTestResource page_198_GETTABUP = LuaTestResource.resolve("ch10/page_198_GETTABUP.lua");
    LuaTestResource page_199_SETTABUP = LuaTestResource.resolve("ch10/page_199_SETTABUP.lua");
    LuaTestResource page_200_JMP = LuaTestResource.resolve("ch10/page_200_JMP.lua");
    LuaTestResource page_202_test = LuaTestResource.resolve("ch10/page_202_test.lua");
    LuaTestResource page_202_test_simpler_case1 =
        LuaTestResource.resolve("ch10/page_202_test_simpler_case1.lua");
    LuaTestResource upvalue_add_case1 = LuaTestResource.resolve("ch10/upvalue_add_case1.lua");
    LuaTestResource upvalue_add_case2 = LuaTestResource.resolve("ch10/upvalue_add_case2.lua");
  }

  interface ch11 {
    LuaTestResource getmetatable_nil = LuaTestResource.resolve("ch11/getmetatable_nil.lua");
    LuaTestResource getmetatable_string = LuaTestResource.resolve("ch11/getmetatable_string.lua");
    LuaTestResource get_from_array = LuaTestResource.resolve("ch11/get_from_array.lua");
    LuaTestResource get_from_array_vararg =
        LuaTestResource.resolve("ch11/get_from_array_vararg.lua");
    LuaTestResource page_206_debug_setmetatable =
        LuaTestResource.resolve("ch11/page_206_debug_setmetatable.lua");
    LuaTestResource page_206_getmetatable =
        LuaTestResource.resolve("ch11/page_206_getmetatable.lua");
    LuaTestResource page_207_metatable_add =
        LuaTestResource.resolve("ch11/page_207_metatable_add.lua");
    LuaTestResource setmetatable_case1 = LuaTestResource.resolve("ch11/setmetatable_case1.lua");
    LuaTestResource sub = LuaTestResource.resolve("ch11/sub.lua");
    LuaTestResource TAILCALL_case1 = LuaTestResource.resolve("ch11/TAILCALL_case1.lua");
    LuaTestResource __eq_always_true = LuaTestResource.resolve("ch11/__eq_always_true.lua");
    LuaTestResource __eq_not_call = LuaTestResource.resolve("ch11/__eq_not_call.lua");
    LuaTestResource __len_fixed = LuaTestResource.resolve("ch11/__len_fixed.lua");
    LuaTestResource __sub_print_self = LuaTestResource.resolve("ch11/__sub_print_self.lua");
  }

  interface ch12 {
    LuaTestResource ipairs_array_1_element =
        LuaTestResource.resolve("ch12/ipairs_array_1_element.lua");
    LuaTestResource ipairs_case1 = LuaTestResource.resolve("ch12/ipairs_case1.lua");
    LuaTestResource ipairs_empty_array = LuaTestResource.resolve("ch12/ipairs_empty_array.lua");
    LuaTestResource page_165_test_simpler_case1 =
        LuaTestResource.resolve("ch12/page_165_test_simpler_case1.lua");
    LuaTestResource page_165_test_simpler_case2 =
        LuaTestResource.resolve("ch12/page_165_test_simpler_case2.lua");
    LuaTestResource page_224_array_iterate =
        LuaTestResource.resolve("ch12/page_224_array_iterate.lua");
    LuaTestResource page_224_for_in = LuaTestResource.resolve("ch12/page_224_for_in.lua");
    LuaTestResource page_225_for_in_with_next =
        LuaTestResource.resolve("ch12/page_225_for_in_with_next.lua");
    LuaTestResource page_226_for_in_with_next_simpler =
        LuaTestResource.resolve("ch12/page_226_for_in_with_next_simpler.lua");
    LuaTestResource page_234_test = LuaTestResource.resolve("ch12/page_234_test.lua");
    LuaTestResource pairs_array_1_element =
        LuaTestResource.resolve("ch12/pairs_array_1_element.lua");
    LuaTestResource pairs_array_3_element =
        LuaTestResource.resolve("ch12/pairs_array_3_element.lua");
    LuaTestResource pairs_empty_array = LuaTestResource.resolve("ch12/pairs_empty_array.lua");
  }

  interface ch13 {
    LuaTestResource error_case1 = LuaTestResource.resolve("ch13/error_case1.lua");
    LuaTestResource page_241_test = LuaTestResource.resolve("ch13/page_241_test.lua");
    LuaTestResource pcall_error = LuaTestResource.resolve("ch13/pcall_error.lua");
    LuaTestResource pcall_print = LuaTestResource.resolve("ch13/pcall_print.lua");
  }
}
