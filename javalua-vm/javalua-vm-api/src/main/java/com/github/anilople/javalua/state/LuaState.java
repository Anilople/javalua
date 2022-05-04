package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.constant.LuaConstants.ThreadStatus;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;
import java.io.PrintStream;

public interface LuaState {

  static LuaState create() {
    return create(LuaConstants.LUA_MIN_STACK, new Prototype());
  }

  static LuaState create(int stackSize, Prototype prototype) {
    return SpiUtils.loadOneInterfaceImpl(
        LuaState.class,
        int.class, Prototype.class,
        stackSize, prototype
    );
  }

  static void printStack(LuaState luaState) {
    printStack(luaState, System.out);
  }

  static void printStack(LuaState luaState, PrintStream printStream) {
    final var top = luaState.getTop();
    for (int i = 1; i <= top; i++) {
      LuaValue luaValue = luaState.toLuaValue(i);
      if (luaValue instanceof LuaClosure) {
        printStream.print("[" + luaValue.getClass().getSimpleName() + "]");
      } else {
        printStream.print("[" + luaValue + "]");
      }
    }
    printStream.println();
  }

  /* basic stack manipulation */
  int getTop();

  int absIndex(int index);

  boolean checkStack(int n);

  void pop(int n);

  /**
   * page 60
   */
  void copy(int from, int to);

  /**
   * 把指定索引处的值推入栈顶
   */
  void pushValue(int index);

  /**
   * {@link #pushValue(int)}的反操作，将栈顶值pop，然后写入指定位置
   *
   * {@code #define lua_replace(L,idx)	(lua_copy(L, -1, (idx)), lua_pop(L, 1))}
   *
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L373">lua.h#L373 lua_replace</a>
   */
  void replace(int index);

  /**
   * 将栈顶值pop，插入指定位置，原index的数据会向后移动1格
   */
  void insert(int index);

  /**
   * page 62. 删除指定索引处的值
   */
  void remove(int index);

  /**
   * 将 [index, top] 索引区间内的值，朝栈顶方向旋转n个位置，
   *
   * 如果n<0，那么实际效果就是朝栈底方向旋转
   */
  void rotate(int index, int n);

  void setTop(int index);

  /* access functions (stack -> Java) */
  String luaTypeName(LuaType type);

  LuaType luaType(int index);

  boolean isLuaNone(int index);

  boolean isLuaNil(int index);

  boolean isLuaNoneOrLuaNil(int index);

  boolean isLuaBoolean(int index);

  boolean isLuaInteger(int index);

  boolean isLuaNumber(int index);

  boolean isLuaString(int index);

  LuaValue popLuaValue();

  LuaValue toLuaValue(int index);

  LuaBoolean toLuaBoolean(int index);

  LuaInteger toLuaInteger(int index);

  Return2<LuaInteger, Boolean> toLuaIntegerX(int index);

  /**
   * @return 返回 0 如果索引对应的值不是 number 类型
   */
  LuaNumber toLuaNumber(int index);

  Return2<LuaNumber, Boolean> toLuaNumberX(int index);

  /**
   * Java的{@link #toString()}有特殊含义，所以命名成 lua string
   *
   * 复用{@link #toLuaStringX(int)}
   */
  LuaString toLuaString(int index);
  /**
   * 会修改栈.
   */
  Return2<LuaString, Boolean> toLuaStringX(int index);

  /* push functions (Java -> stack) */
  void pushLuaNil();

  void pushLuaBoolean(LuaBoolean b);

  void pushLuaInteger(LuaInteger value);

  void pushLuaNumber(LuaNumber value);

  void pushLuaString(LuaString value);

  void arithmetic(ArithmeticOperator operator);

  void bitwise(BitwiseOperator operator);

  /**
   * 不改变栈的状态
   */
  LuaBoolean compare(int index1, int index2, ComparisonOperator operator);

  void len(int index);

  /**
   * 如果 n = 0，push 空字符；
   */
  void concat(int n);

  void newTable();

  void createTable(int arraySize, int mapSize);

  /**
   * index对应的值作为table，栈顶的值作为key，得到value，把value放入栈顶
   */
  LuaType getTable(int index);

  LuaType getField(int index, LuaString key);
  /**
   * 根据索引获取数组元素
   * @param index table的所在处
   * @param key 数组的索引
   */
  LuaType getI(int index, LuaInteger key);

  /**
   * 把栈顶的key和value放入index对应的table中
   */
  void setTable(int index);

  void setField(int index, LuaString key);

  /**
   * index对应的值作为table，栈顶的值作为value
   */
  void setI(int index, LuaInteger key);

  /**
   * pop函数调用帧
   */
  void popCallFrame();

  /**
   * page 148
   *
   * 把主函数原型实例化为闭包并推入栈顶
   *
   * 如果加载失败，需要在栈顶留下一条错误信息
   *
   * @param binaryChunk chunk数据
   * @param chunkName chunk的名字，供加载错误或者调试的时候使用
   * @param mode 加载模式 b t 或者 bt
   * @return 0 如果加载成功，非0如果加载失败
   */
  int load(byte[] binaryChunk, String chunkName, String mode);

  /**
   * 对lua函数进行调用
   *
   * @param nArgs 函数的参数个数
   * @param numberOfResultsWanted 需要的返回值个数。如果是-1，被调函数的返回值会全部留在栈顶，返回值不会被pop
   * @return 调用函数的栈帧（已经被pop）
   */
  CallFrame call(int nArgs, int numberOfResultsWanted);

  void pushJavaFunction(JavaFunction javaFunction);

  boolean isJavaFunction(int index);

  /**
   *
   * @return null 如果索引位置的 值不是 {@link JavaFunction}
   */
  JavaFunction toJavaFunction(int index);

  /* 用来操作全局环境的4个api */

  /**
   * 把全局环境推入栈顶，以备后续操作使用
   */
  void pushGlobalTable();

  /**
   * 把全局环境中，某个字段对应的value推入栈顶
   * @param name 字段的名字
   * @return value的类型
   */
  LuaType getGlobal(LuaString name);

  /**
   * 往全局环境中写一个值，值的字段名（key）由参数指定，value从栈顶pop
   * @param name 值的字段名（key）
   */
  void setGlobal(LuaString name);

  /**
   * 给全局环境注册Java函数值，仅操作全局环境
   * @param name 函数名
   * @param javaFunction 函数
   */
  void register(LuaString name, JavaFunction javaFunction);

  /**
   * page 193 添加对upvalue的支持
   *
   * 和{@link #pushJavaFunction(JavaFunction)}的区别在于，这个方法会先从栈顶弹出n个Lua值，这些值会成为Java闭包的Upvalue
   */
  void pushJavaClosure(JavaFunction javaFunction, int n);

  /**
   * 看索引 index 指定的 value，是否有对应的元表，
   * 如果有，则把元表推入栈顶并返回true；否则栈的状态不改变，并返回false
   */
  boolean getMetaTable(int index);

  /**
   * 从栈顶弹出1个table t，然后找到指定索引 index 处对应的value，把这个value对应的元表改成 t
   */
  void setMetaTable(int index);

  /* rawXxx 带raw作为前缀的方法，在执行过程中，不会触发元方法 */
  /**
   * 与{@link #len(int)}不同的是，这个方法不会去触发 元方法 __len
   */
  void rawLen(int index);

  boolean rawEqual(int index1, int index2);

  LuaType rawGet(int index);

  void rawSet(int index);

  LuaType rawGetI(int index, LuaInteger i);

  void rawSetI(int index, LuaInteger i);

  /**
   * page 228
   *
   * 表从索引处获取，
   * key从栈顶弹出
   *
   * @param index 表的索引
   * @return true如果还没开始遍历，或者遍历没有结束；false如果遍历已经结束
   */
  boolean next(int index);

  /**
   * 抛异常
   *
   * 从栈顶弹出1个Lua值，把该值作为错误抛出
   * 对应lua函数 error
   */
  int error();

  /**
   * 对应lua函数 pcall
   *
   * @param nArgs 函数参数个数
   * @param nResults 函数返回参数个数
   * @param msgh 指定错误处理器，第13章暂不考虑
   * @return 错误码
   */
  ThreadStatus pcall(int nArgs, int nResults, int msgh);
}
