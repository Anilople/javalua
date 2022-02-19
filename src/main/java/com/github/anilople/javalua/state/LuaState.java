package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.util.Return2;
import java.io.PrintStream;

public interface LuaState {

  static LuaState create() {
    return new DefaultLuaStateImpl(20, new Prototype());
  }

  static LuaState create(int stackSize, Prototype prototype) {
    return new DefaultLuaStateImpl(stackSize, prototype);
  }

  static void printStack(LuaState luaState) {
    printStack(luaState, System.out);
  }

  static void printStack(LuaState luaState, PrintStream printStream) {
    final var top = luaState.getTop();
    for (int i = 1; i <= top; i++) {
      LuaValue luaValue = luaState.toLuaValue(i);
      printStream.print("[" + luaValue.toString() + "]");
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

  void remove(int index);

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
   * index对应的值作为table，栈顶的值作为key
   */
  LuaType getTable(int index);

  LuaType getField(int index, LuaString key);
  /**
   * 根据索引获取数组元素
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
   * @param nResults 需要的返回值数量，如果是-1，被调函数的返回值会全部留在栈顶
   */
  void call(int nArgs, int nResults);
}
