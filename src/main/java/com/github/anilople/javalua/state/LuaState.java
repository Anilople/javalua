package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
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

  void pushValue(int index);

  /**
   * {@code #define lua_replace(L,idx)	(lua_copy(L, -1, (idx)), lua_pop(L, 1))}
   *
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L373">lua.h#L373 lua_replace</a>
   */
  void replace(int index);

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
   * 返回当前pc，不是必须的方法，仅测试使用
   */
  int pc();

  /**
   * 修改PC（用来实现跳转指令）
   */
  void addPC(int n);

  /**
   * 取出当前指令，将PC指向下一条指令
   * <p>
   * 虚拟机循环会使用，LOADKX等少数几个指令也会用到
   */
  Instruction fetch();

  /**
   * 将指定常量推入栈顶
   * <p>
   * LOADK和LOADKX会使用
   */
  void getConst(int index);

  /**
   * page 94
   * <p>
   * 将指定常量或栈值推入栈顶
   * <p>
   * 不是必须的方法，放这里是为了方便指令的实现，例如算术运算指令
   * <p>
   * 传递的参数实际上是 {@link com.github.anilople.javalua.instruction.Instruction.Opcode.OpMode#iABC} 模式指令里的
   * {@link com.github.anilople.javalua.instruction.Instruction.Opcode.OpArgMask#OpArgK} 类型，总共 9 bits
   * <p>
   * 如果最高位是1，那么参数里存放的是常量表索引，把最高位去掉就可以得到索引值。 如果最高位是0，参数里存放的就是寄存器索引值
   */
  void getRK(int rk);

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
