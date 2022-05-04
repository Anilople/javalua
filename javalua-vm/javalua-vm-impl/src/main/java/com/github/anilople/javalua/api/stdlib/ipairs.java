package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.instruction.operator.Arithmetic;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaState;

/**
 * page 233
 *
 * @author wxq
 */
public class ipairs extends AbstractJavaFunction {

  private static final ipairs INSTANCE = new ipairs();

  public static ipairs getInstance() {
    return INSTANCE;
  }

  @Override
  public Integer apply(LuaState luaState) {
    luaState.pushJavaFunction(_iPairsAux.INSTANCE);
    // 把 table 推入栈顶
    luaState.pushValue(1);
    // 初始值
    // page 233 放的是 0
    luaState.pushLuaInteger(LuaInteger.newLuaInteger(0));
    return 3;
  }

  /**
   * page 233
   *
   * 数组版本的next
   *
   * 返回 nil 或者 2个结果
   */
  static class _iPairsAux extends AbstractJavaFunction {

    static final _iPairsAux INSTANCE = new _iPairsAux();

    /**
     * 第1个参数是table，第2个参数是上次遍历的下标
     */
    @Override
    public Integer apply(LuaState luaState) {
      LuaInteger index = luaState.toLuaInteger(2);
      // 初始时是1
      LuaInteger nextIndex = Arithmetic.addLuaInteger(index, LuaInteger.newLuaInteger(1));

      // push next index
      luaState.pushLuaInteger(nextIndex);
      // push next value
      luaState.getI(1, nextIndex);

      // next value is nil or not
      if (luaState.isLuaNil(-1)) {
        return 1;
      } else {
        return 2;
      }
    }
  }
}
