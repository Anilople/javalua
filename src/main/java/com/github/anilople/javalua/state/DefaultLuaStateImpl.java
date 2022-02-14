package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.instruction.operator.Length;
import com.github.anilople.javalua.instruction.operator.StringConcat;
import com.github.anilople.javalua.util.Return2;

public class DefaultLuaStateImpl implements LuaState {

  protected final LuaStack luaStack;

  protected final Prototype prototype;

  protected int pc;

  protected DefaultLuaStateImpl(int stackSize, Prototype prototype) {
    this.luaStack = new LuaStack(stackSize);
    this.prototype = prototype;
    this.pc = 0;
  }

  @Override
  public int getTop() {
    return luaStack.getTop();
  }

  @Override
  public int absIndex(int index) {
    return luaStack.absIndex(index);
  }

  @Override
  public boolean checkStack(int n) {
    luaStack.check(n);
    return true;
  }

  @Override
  public void pop(int n) {
    for (int i = 0; i < n; i++) {
      luaStack.pop();
    }
  }

  @Override
  public void copy(int from, int to) {
    var luaValue = luaStack.get(from);
    luaStack.set(to, luaValue);
  }

  @Override
  public void pushValue(int index) {
    var value = luaStack.get(index);
    luaStack.push(value);
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
    final int toIndex = luaStack.getTop();
    final int fromIndex = this.absIndex(index);
    final int length = toIndex - fromIndex + 1;
    final int movedIndexCount = n % length;
    final int midIndex;
    if (movedIndexCount >= 0) {
      midIndex = toIndex - movedIndexCount;
    } else {
      midIndex = fromIndex - movedIndexCount - 1;
    }
    luaStack.reverse(fromIndex, midIndex);
    luaStack.reverse(midIndex + 1, toIndex);
    luaStack.reverse(fromIndex, toIndex);
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
    var value = luaStack.get(index);
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
  public LuaBoolean toLuaBoolean(int index) {
    var value = luaStack.get(index);
    return LuaBoolean.from(value);
  }

  @Override
  public LuaInteger toLuaInteger(int index) {
    var r = toLuaIntegerX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaInteger, Boolean> toLuaIntegerX(int index) {
    var luaValue = luaStack.get(index);
    return LuaInteger.from(luaValue);
  }

  @Override
  public LuaNumber toLuaNumber(int index) {
    var r = toLuaNumberX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaNumber, Boolean> toLuaNumberX(int index) {
    var luaValue = luaStack.get(index);
    return LuaNumber.from(luaValue);
  }

  @Override
  public LuaString toLuaString(int index) {
    var r = toLuaStringX(index);
    return r.r0;
  }

  @Override
  public Return2<LuaString, Boolean> toLuaStringX(int index) {
    final var luaValue = luaStack.get(index);
    var r = LuaString.from(luaValue);
    if (isLuaNumber(index)) {
      // 改变 栈
      luaStack.set(index, r.r0);
    }
    return r;
  }

  @Override
  public void pushLuaNil() {
    luaStack.push(LuaValue.NIL);
  }

  @Override
  public void pushLuaBoolean(LuaBoolean b) {
    luaStack.push(b);
  }

  @Override
  public void pushLuaInteger(LuaInteger value) {
    luaStack.push(value);
  }

  @Override
  public void pushLuaNumber(LuaNumber value) {
    luaStack.push(value);
  }

  @Override
  public void pushLuaString(LuaString value) {
    luaStack.push(value);
  }

  @Override
  public void arithmetic(ArithmeticOperator operator) {
    // 先pop b，再pop a
    var b = luaStack.pop();
    final LuaValue result;
    if (ArithmeticOperator.LUA_OPUNM.equals(operator)) {
      result = operator.getOperator().apply(b, null);
    } else {
      var a = luaStack.pop();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    luaStack.push(result);
  }

  @Override
  public void bitwise(BitwiseOperator operator) {
    var a = luaStack.pop();
    final LuaValue result;
    if (BitwiseOperator.LUA_OPBNOT.equals(operator)) {
      result = operator.getOperator().apply(a, null);
    } else {
      var b = luaStack.pop();
      result = operator.getOperator().apply(a, b);
    }
    assert result != null;
    luaStack.push(result);
  }

  @Override
  public LuaBoolean compare(int index1, int index2, ComparisonOperator operator) {
    var a = luaStack.get(index1);
    var b = luaStack.get(index2);
    return operator.getOperator().apply(a, b);
  }

  @Override
  public void len(int index) {
    var a = luaStack.get(index);
    var len = Length.length(a);
    luaStack.push(len);
  }

  @Override
  public void concat(int n) {
    if (0 == n) {
      luaStack.push(LuaValue.of(""));
    } else {
      for (; n >= 2; n--) {
        var b = luaStack.pop();
        var a = luaStack.pop();
        var result = StringConcat.concat(a, b);
        luaStack.push(result);
      }
    }
  }
}
