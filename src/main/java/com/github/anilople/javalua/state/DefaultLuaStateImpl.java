package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.instruction.operator.Length;
import com.github.anilople.javalua.instruction.operator.StringConcat;
import com.github.anilople.javalua.util.Return2;

public class DefaultLuaStateImpl implements LuaState {

  protected final CallStack callStack;

  protected final Prototype prototype;

  protected final Instruction[] instructions;

  protected DefaultLuaStateImpl(int stackSize, Prototype prototype) {
    this.callStack = CallStack.of(stackSize);
    this.prototype = prototype;
    this.instructions = this.prototype.getCode().getInstructions();
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
    var luaValue = this.callStack.topCallFrame().get(from);
    this.callStack.topCallFrame().set(to, luaValue);
  }

  @Override
  public void pushValue(int index) {
    var value = this.callStack.topCallFrame().get(index);
    this.callStack.topCallFrame().push(value);
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
    var value = this.callStack.topCallFrame().get(index);
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

  @Override
  public LuaValue toLuaValue(int index) {
    return this.callStack.topCallFrame().get(index);
  }

  @Override
  public LuaBoolean toLuaBoolean(int index) {
    var value = this.callStack.topCallFrame().get(index);
    return LuaBoolean.from(value);
  }

  @Override
  public LuaInteger toLuaInteger(int index) {
    var r = toLuaIntegerX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaInteger, Boolean> toLuaIntegerX(int index) {
    var luaValue = this.callStack.topCallFrame().get(index);
    return LuaInteger.from(luaValue);
  }

  @Override
  public LuaNumber toLuaNumber(int index) {
    var r = toLuaNumberX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaNumber, Boolean> toLuaNumberX(int index) {
    var luaValue = this.callStack.topCallFrame().get(index);
    return LuaNumber.from(luaValue);
  }

  @Override
  public LuaString toLuaString(int index) {
    var r = toLuaStringX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaString, Boolean> toLuaStringX(int index) {
    final var luaValue = this.callStack.topCallFrame().get(index);
    var r = LuaString.from(luaValue);
    if (isLuaNumber(index)) {
      // 改变 栈
      this.callStack.topCallFrame().set(index, r.r0);
    }
    return r;
  }

  @Override
  public void pushLuaNil() {
    this.callStack.topCallFrame().push(LuaValue.NIL);
  }

  @Override
  public void pushLuaBoolean(LuaBoolean b) {
    this.callStack.topCallFrame().push(b);
  }

  @Override
  public void pushLuaInteger(LuaInteger value) {
    this.callStack.topCallFrame().push(value);
  }

  @Override
  public void pushLuaNumber(LuaNumber value) {
    this.callStack.topCallFrame().push(value);
  }

  @Override
  public void pushLuaString(LuaString value) {
    this.callStack.topCallFrame().push(value);
  }

  @Override
  public void arithmetic(ArithmeticOperator operator) {
    // 先pop b，再pop a
    var b = this.callStack.topCallFrame().pop();
    final LuaValue result;
    if (ArithmeticOperator.LUA_OPUNM.equals(operator)) {
      result = operator.getOperator().apply(b, null);
    } else {
      var a = this.callStack.topCallFrame().pop();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    this.callStack.topCallFrame().push(result);
  }

  @Override
  public void bitwise(BitwiseOperator operator) {
    var a = this.callStack.topCallFrame().pop();
    final LuaValue result;
    if (BitwiseOperator.LUA_OPBNOT.equals(operator)) {
      result = operator.getOperator().apply(a, null);
    } else {
      var b = this.callStack.topCallFrame().pop();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    this.callStack.topCallFrame().push(result);
  }

  @Override
  public LuaBoolean compare(int index1, int index2, ComparisonOperator operator) {
    var a = this.callStack.topCallFrame().get(index1);
    var b = this.callStack.topCallFrame().get(index2);
    return operator.getOperator().apply(a, b);
  }

  @Override
  public void len(int index) {
    var a = this.callStack.topCallFrame().get(index);
    var len = Length.length(a);
    this.callStack.topCallFrame().push(len);
  }

  @Override
  public void concat(int n) {
    if (0 == n) {
      this.callStack.topCallFrame().push(LuaValue.of(""));
    } else {
      for (; n >= 2; n--) {
        var b = this.callStack.topCallFrame().pop();
        var a = this.callStack.topCallFrame().pop();
        var result = StringConcat.concat(a, b);
        this.callStack.topCallFrame().push(result);
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
    this.callStack.topCallFrame().push(luaTable);
  }

  LuaType getTable(LuaValue table, LuaValue key) {
    if (!(table instanceof LuaTable)) {
      throw new IllegalStateException("not a table! It is " + table);
    }
    LuaTable luaTable = (LuaTable) table;
    var value = luaTable.get(key);
    this.callStack.topCallFrame().push(value);
    return value.type();
  }

  @Override
  public LuaType getTable(int index) {
    var table = this.callStack.topCallFrame().get(index);
    var key = this.callStack.topCallFrame().pop();
    return this.getTable(table, key);
  }

  @Override
  public LuaType getField(int index, LuaString key) {
    var table = this.callStack.topCallFrame().get(index);
    return this.getTable(table, key);
  }

  @Override
  public LuaType getI(int index, LuaInteger key) {
    var table = this.callStack.topCallFrame().get(index);
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
    var table = this.callStack.topCallFrame().get(index);
    var value = this.callStack.topCallFrame().pop();
    var key = this.callStack.topCallFrame().pop();
    this.setTable(table, key, value);
  }

  @Override
  public void setField(int index, LuaString key) {
    var table = this.callStack.topCallFrame().get(index);
    var value = this.callStack.topCallFrame().pop();
    this.setTable(table, key, value);
  }

  @Override
  public void setI(int index, LuaInteger key) {
    var table = this.callStack.topCallFrame().get(index);
    var value = this.callStack.topCallFrame().pop();
    this.setTable(table, key, value);
  }

  @Override
  public void popCallFrame() {}

  @Override
  public int load(byte[] binaryChunk, String chunkName, String mode) {
    if (!"b".equals(mode)) {
      throw new IllegalArgumentException("not support mode '" + mode + "' yet");
    }
    var prototype = BinaryChunk.getPrototype(binaryChunk);
    LuaClosure luaClosure = new LuaClosure(prototype);
    this.callStack.topCallFrame().push(luaClosure);
    return 0;
  }

  void callLuaClosure(LuaClosure luaClosure, int nArgs, int nResults) {
    var args = this.callStack.topCallFrame().popN(nArgs);
    // pop function
    this.callStack.topCallFrame().pop();

    this.callStack.pushCallFrame(luaClosure, nArgs, args);
    this.runLuaClosure();
    var functionCallFrame = this.callStack.popCallFrame();

    if (0 != nResults) {
      // 把被调函数的所有返回值 放入 当前的 调用帧
      var results = functionCallFrame.popResults();
      this.callStack.topCallFrame().check(results.length);
      this.callStack.topCallFrame().pushN(results);
    }
  }

  protected void runLuaClosure() {
    throw new UnsupportedOperationException("please implement it in subclass");
  }

  @Override
  public void call(int nArgs, int nResults) {
    final LuaClosure luaClosure;
    {
      LuaValue luaValue = this.callStack.topCallFrame().get(-(nArgs + 1));
      if (luaValue instanceof LuaClosure) {
        luaClosure = (LuaClosure) luaValue;
      } else {
        throw new IllegalStateException(luaValue + "'s type isn't " + LuaClosure.class);
      }
    }
    this.callLuaClosure(luaClosure, nArgs, nResults);
  }
}
