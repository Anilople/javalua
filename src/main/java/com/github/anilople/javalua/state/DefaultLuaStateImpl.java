package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.instruction.operator.Length;
import com.github.anilople.javalua.instruction.operator.StringConcat;
import com.github.anilople.javalua.util.Return2;
import java.util.function.Consumer;

public class DefaultLuaStateImpl implements LuaState {

  protected final CallStack callStack;

  /**
   * page 172.
   *
   * Lua注册表
   */
  protected final LuaTable registry;

  protected DefaultLuaStateImpl(int stackSize, Prototype prototype) {
    this.callStack = CallStack.of(stackSize, prototype);
    this.registry = LuaTable.of(0, 0);
    // page 172
    this.registry.put(LuaConstants.LUA_RIDX_GLOBALS, LuaTable.of(0, 0));
  }

  /**
   * @return 全局环境 _ENV
   */
  LuaTable getEnv() {
    return (LuaTable) this.registry.get(LuaConstants.LUA_RIDX_GLOBALS);
  }

  @Override
  public int getTop() {
    return this.callStack.topCallFrame().getTop();
  }

  @Override
  public int absIndex(int index) {
    return this.callStack.topCallFrame().absIndex(index);
  }

  @Override
  public boolean checkStack(int n) {
    this.callStack.topCallFrame().check(n);
    return true;
  }

  @Override
  public void pop(int n) {
    for (int i = 0; i < n; i++) {
      this.callStack.topCallFrame().pop();
    }
  }

  @Override
  public void copy(int from, int to) {
    var luaValue = this.getLuaValue(from);
    this.callStack.topCallFrame().set(to, luaValue);
  }

  @Override
  public void pushValue(int index) {
    var value = this.getLuaValue(index);
    this.pushLuaValue(value);
  }

  @Override
  public void replace(int index) {
    // #define lua_replace(L,idx)	(lua_copy(L, -1, (idx)), lua_pop(L, 1))
    this.copy(-1, index);
    // index 对应的地方可能就是栈顶
    this.pop(1);
  }

  @Override
  public void insert(int index) {
    this.rotate(index, 1);
  }

  @Override
  public void remove(int index) {
    this.rotate(index, -1);
    this.pop(1);
  }

  @Override
  public void rotate(int index, int n) {
    // inclusive
    final int toIndex = this.callStack.topCallFrame().getTop();
    final int fromIndex = this.absIndex(index);
    final int length = toIndex - fromIndex + 1;
    final int movedIndexCount = n % length;
    final int midIndex;
    if (movedIndexCount >= 0) {
      midIndex = toIndex - movedIndexCount;
    } else {
      midIndex = fromIndex - movedIndexCount - 1;
    }
    this.callStack.topCallFrame().reverse(fromIndex, midIndex);
    this.callStack.topCallFrame().reverse(midIndex + 1, toIndex);
    this.callStack.topCallFrame().reverse(fromIndex, toIndex);
  }

  @Override
  public void setTop(int index) {
    final var newTop = this.absIndex(index);
    if (newTop < 0) {
      throw new IllegalStateException("new Top " + newTop + " < 0 index = " + index);
    }
    while (newTop > this.getTop()) {
      this.pushLuaNil();
    }
    while (newTop < this.getTop()) {
      this.pop(1);
    }
  }

  @Override
  public String luaTypeName(LuaType luaType) {
    return luaType.getTypeName();
  }

  @Override
  public LuaType luaType(int index) {
    var value = this.getLuaValue(index);
    return value.type();
  }

  @Override
  public boolean isLuaNone(int index) {
    var type = this.luaType(index);
    return LuaType.LUA_TNONE.equals(type);
  }

  @Override
  public boolean isLuaNil(int index) {
    var type = this.luaType(index);
    return LuaType.LUA_TNIL.equals(type);
  }

  @Override
  public boolean isLuaNoneOrLuaNil(int index) {
    if (this.isLuaNone(index)) {
      return true;
    }
    if (this.isLuaNil(index)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isLuaBoolean(int index) {
    var luaType = this.luaType(index);
    return LuaType.LUA_TBOOLEAN.equals(luaType);
  }

  @Override
  public boolean isLuaInteger(int index) {
    var luaType = this.luaType(index);
    return LuaType.LUA_TNUMBER.equals(luaType);
  }

  @Override
  public boolean isLuaNumber(int index) {
    var luaType = this.luaType(index);
    return LuaType.LUA_TNUMBER.equals(luaType);
  }

  @Override
  public boolean isLuaString(int index) {
    var luaType = this.luaType(index);
    return LuaType.LUA_TSTRING.equals(luaType);
  }

  /**
   * 这个class中，如果想获取index上的{@link LuaValue}，需要使用这个方法
   */
  LuaValue getLuaValue(int index) {
    if (index == LuaConstants.LUA_REGISTRY_INDEX) {
      return this.registry;
    }
    return this.callStack.topCallFrame().get(index);
  }

  void pushLuaValue(LuaValue luaValue) {
    this.callStack.topCallFrame().push(luaValue);
  }

  LuaValue popLuaValue() {
    return this.callStack.topCallFrame().pop();
  }

  @Override
  public LuaValue toLuaValue(int index) {
    return this.getLuaValue(index);
  }

  @Override
  public LuaBoolean toLuaBoolean(int index) {
    var value = this.getLuaValue(index);
    return LuaBoolean.from(value);
  }

  @Override
  public LuaInteger toLuaInteger(int index) {
    var r = toLuaIntegerX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaInteger, Boolean> toLuaIntegerX(int index) {
    var luaValue = this.getLuaValue(index);
    return LuaInteger.from(luaValue);
  }

  @Override
  public LuaNumber toLuaNumber(int index) {
    var r = toLuaNumberX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaNumber, Boolean> toLuaNumberX(int index) {
    var luaValue = this.getLuaValue(index);
    return LuaNumber.from(luaValue);
  }

  @Override
  public LuaString toLuaString(int index) {
    var r = toLuaStringX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaString, Boolean> toLuaStringX(int index) {
    final var luaValue = this.getLuaValue(index);
    var r = LuaString.from(luaValue);
    if (isLuaNumber(index)) {
      // 改变 栈
      this.callStack.topCallFrame().set(index, r.r0);
    }
    return r;
  }

  @Override
  public void pushLuaNil() {
    this.pushLuaValue(LuaValue.NIL);
  }

  @Override
  public void pushLuaBoolean(LuaBoolean b) {
    this.pushLuaValue(b);
  }

  @Override
  public void pushLuaInteger(LuaInteger value) {
    this.pushLuaValue(value);
  }

  @Override
  public void pushLuaNumber(LuaNumber value) {
    this.pushLuaValue(value);
  }

  @Override
  public void pushLuaString(LuaString value) {
    this.pushLuaValue(value);
  }

  @Override
  public void arithmetic(ArithmeticOperator operator) {
    // 先pop b，再pop a
    var b = this.popLuaValue();
    final LuaValue result;
    if (ArithmeticOperator.LUA_OPUNM.equals(operator)) {
      result = operator.getOperator().apply(b, null);
    } else {
      var a = this.popLuaValue();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    this.pushLuaValue(result);
  }

  @Override
  public void bitwise(BitwiseOperator operator) {
    var a = this.popLuaValue();
    final LuaValue result;
    if (BitwiseOperator.LUA_OPBNOT.equals(operator)) {
      result = operator.getOperator().apply(a, null);
    } else {
      var b = this.popLuaValue();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    this.pushLuaValue(result);
  }

  @Override
  public LuaBoolean compare(int index1, int index2, ComparisonOperator operator) {
    var a = this.getLuaValue(index1);
    var b = this.getLuaValue(index2);
    return operator.getOperator().apply(a, b);
  }

  @Override
  public void len(int index) {
    var a = this.getLuaValue(index);
    var len = Length.length(a);
    this.pushLuaValue(len);
  }

  @Override
  public void concat(int n) {
    if (0 == n) {
      this.pushLuaValue(LuaValue.of(""));
    } else {
      for (; n >= 2; n--) {
        var b = this.popLuaValue();
        var a = this.popLuaValue();
        var result = StringConcat.concat(a, b);
        this.pushLuaValue(result);
      }
    }
  }

  @Override
  public void newTable() {
    this.createTable(0, 0);
  }

  @Override
  public void createTable(int arraySize, int mapSize) {
    LuaTable luaTable = LuaTable.of(arraySize, mapSize);
    this.pushLuaValue(luaTable);
  }

  LuaType getTable(LuaValue table, LuaValue key) {
    if (!(table instanceof LuaTable)) {
      throw new IllegalStateException("not a table! It is " + table);
    }
    LuaTable luaTable = (LuaTable) table;
    var value = luaTable.get(key);
    this.pushLuaValue(value);
    return value.type();
  }

  @Override
  public LuaType getTable(int index) {
    var table = this.getLuaValue(index);
    var key = this.popLuaValue();
    return this.getTable(table, key);
  }

  @Override
  public LuaType getField(int index, LuaString key) {
    var table = this.getLuaValue(index);
    return this.getTable(table, key);
  }

  @Override
  public LuaType getI(int index, LuaInteger key) {
    var table = this.getLuaValue(index);
    return this.getTable(table, key);
  }

  void setTable(LuaValue table, LuaValue key, LuaValue value) {
    if (!(table instanceof LuaTable)) {
      // TODO, page 130 第11章 再 完善
      throw new IllegalStateException("not a table! It is " + table);
    }
    LuaTable luaTable = (LuaTable) table;
    luaTable.put(key, value);
  }

  @Override
  public void setTable(int index) {
    var table = this.getLuaValue(index);
    var value = this.popLuaValue();
    var key = this.popLuaValue();
    this.setTable(table, key, value);
  }

  @Override
  public void setField(int index, LuaString key) {
    var table = this.getLuaValue(index);
    var value = this.popLuaValue();
    this.setTable(table, key, value);
  }

  @Override
  public void setI(int index, LuaInteger key) {
    var table = this.getLuaValue(index);
    var value = this.popLuaValue();
    this.setTable(table, key, value);
  }

  @Override
  public void popCallFrame() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int load(byte[] binaryChunk, String chunkName, String mode) {
    if (!"b".equals(mode)) {
      throw new IllegalArgumentException("not support mode '" + mode + "' yet");
    }
    var prototype = BinaryChunk.getPrototype(binaryChunk);
    final LuaClosure luaClosure;
    if (prototype.getUpvalues().length > 0) {
      // 设置 _ENV page 191
      var env = this.getEnv();
      luaClosure = new LuaClosure(prototype);
      luaClosure.setLuaUpvalue(0, LuaUpvalue.newFixedLuaUpvalue(env));
    } else {
      // 不需要管 Upvalue?
      throw new IllegalStateException("_ENV ?");
    }
    this.pushLuaValue(luaClosure);
    return 0;
  }

  CallFrame callLuaClosure(LuaClosure luaClosure, int nArgs, int nResults) {
    var allArgs = this.callStack.topCallFrame().popN(nArgs);
    // pop function
    this.popLuaValue();

    this.callStack.pushCallFrameForPrototype(luaClosure, allArgs);
    this.runLuaClosure();
    var functionCallFrame = this.callStack.popCallFrame();

    if (0 != nResults) {
      // 把被调函数的所有返回值 放入 当前的 调用帧
      var results = functionCallFrame.popResults();
      this.callStack.topCallFrame().check(results.length);
      this.callStack.topCallFrame().pushN(results, nResults);
    }
    return functionCallFrame;
  }

  protected void runLuaClosure() {
    throw new UnsupportedOperationException("please implement it in subclass");
  }

  CallFrame callJavaClosure(LuaClosure luaClosure, int nArgs, int nResults) {
    var allArgs = this.callStack.topCallFrame().popN(nArgs);
    // pop function
    this.popLuaValue();

    // push Java函数用的栈帧
    this.callStack.pushCallFrameForJavaFunction(luaClosure, allArgs);
    // 调用Java函数
    int numberOfElementsFunctionReturned = luaClosure.javaFunction.apply(this);
    // pop Java函数用的栈帧
    var functionCallFrame = this.callStack.popCallFrame();
    if (nResults != 0) {
      var results = functionCallFrame.popN(numberOfElementsFunctionReturned);
      this.checkStack(results.length);
      this.callStack.topCallFrame().pushN(results, nResults);
    }
    return functionCallFrame;
  }

  @Override
  public CallFrame call(int nArgs, int nResults) {
    final LuaClosure luaClosure;
    {
      int indexOfLuaClosure = -(nArgs + 1);
      LuaValue luaValue = this.getLuaValue(indexOfLuaClosure);
      if (luaValue instanceof LuaClosure) {
        luaClosure = (LuaClosure) luaValue;
      } else {
        throw new IllegalStateException(luaValue + "'s type isn't " + LuaClosure.class);
      }
    }
    if (luaClosure.prototype != null) {
      return this.callLuaClosure(luaClosure, nArgs, nResults);
    } else {
      return this.callJavaClosure(luaClosure, nArgs, nResults);
    }
  }

  @Override
  public void pushJavaFunction(JavaFunction javaFunction) {
    LuaClosure luaClosure = new LuaClosure(javaFunction);
    this.pushLuaValue(luaClosure);
  }

  @Override
  public boolean isJavaFunction(int index) {
    LuaValue luaValue = this.toLuaValue(index);
    if (!(luaValue instanceof LuaClosure)) {
      return false;
    }
    LuaClosure luaClosure = (LuaClosure) luaValue;
    return null != luaClosure.javaFunction;
  }

  @Override
  public JavaFunction toJavaFunction(int index) {
    LuaValue luaValue = this.toLuaValue(index);
    if (!(luaValue instanceof LuaClosure)) {
      return null;
    }
    LuaClosure luaClosure = (LuaClosure) luaValue;
    return luaClosure.javaFunction;
  }

  LuaValue getGlobalTable() {
    return this.registry.get(LuaConstants.LUA_RIDX_GLOBALS);
  }

  @Override
  public void pushGlobalTable() {
    var globalTable = this.getGlobalTable();
    this.pushLuaValue(globalTable);
  }

  @Override
  public LuaType getGlobal(LuaString name) {
    var globalTable = this.getGlobalTable();
    return this.getTable(globalTable, name);
  }

  @Override
  public void setGlobal(LuaString name) {
    var globalTable = this.getGlobalTable();
    var value = this.popLuaValue();
    this.setTable(globalTable, name, value);
  }

  @Override
  public void register(LuaString name, JavaFunction javaFunction) {
    this.pushJavaFunction(javaFunction);
    this.setGlobal(name);
  }

  @Override
  public void pushJavaClosure(JavaFunction javaFunction, int n) {
    LuaClosure luaClosure = LuaClosure.newJavaClosure(javaFunction, n);
    for (int i = n - 1; i >= 0; i--) {
      LuaValue luaValue = this.popLuaValue();
      LuaUpvalue luaUpvalue = LuaUpvalue.newFixedLuaUpvalue(luaValue);
      luaClosure.setLuaUpvalue(i, luaUpvalue);
    }
    this.pushLuaValue(luaClosure);
  }
}
