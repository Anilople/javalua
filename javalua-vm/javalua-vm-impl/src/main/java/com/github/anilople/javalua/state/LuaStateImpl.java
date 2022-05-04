package com.github.anilople.javalua.state;

import static com.github.anilople.javalua.instruction.operator.ComparisonOperator.*;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.BinaryChunk;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.constant.LuaConstants.MetaMethod;
import com.github.anilople.javalua.constant.LuaConstants.ThreadStatus;
import com.github.anilople.javalua.exception.LuaErrorRuntimeException;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.Comparison;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.instruction.operator.Length;
import com.github.anilople.javalua.instruction.operator.StringConcat;
import com.github.anilople.javalua.util.Return2;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class LuaStateImpl implements LuaState {

  protected final CallStack callStack;

  /**
   * page 172.
   * <p>
   * Lua注册表
   */
  protected final LuaTable registry =
      LuaTable.of(0, 0)
          // page 172
          .put(LuaConstants.LUA_RIDX_GLOBALS, LuaTable.of(0, 0));

  public LuaStateImpl() {
    throw new UnsupportedOperationException();
  }

  public LuaStateImpl(int stackSize, Prototype prototype) {
    this.callStack = CallStack.of(stackSize, prototype);
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
    if (length <= 0) {
      throw new IllegalArgumentException(
          "need length > 0 now length = "
              + length
              + ", from index "
              + fromIndex
              + " to index "
              + toIndex);
    }
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

  @Override
  public LuaValue popLuaValue() {
    return this.callStack.topCallFrame().pop();
  }

  @Override
  public LuaValue toLuaValue(int index) {
    return this.getLuaValue(index);
  }

  <T extends LuaValue> void throwIllegalStateExceptionIfConvertFail(
      int index, Return2<T, Boolean> return2, Class<T> clazz) {
    if (!return2.r1) {
      LuaValue luaValue = this.getLuaValue(index);
      throw new IllegalStateException(
          "cannot convert lua value "
              + luaValue
              + " in index "
              + index
              + " to "
              + clazz
              + ", please check before convert");
    }
  }

  @Override
  public LuaBoolean toLuaBoolean(int index) {
    var value = this.getLuaValue(index);
    return LuaBoolean.from(value);
  }

  @Override
  public LuaInteger toLuaInteger(int index) {
    var r = toLuaIntegerX(index);
    throwIllegalStateExceptionIfConvertFail(index, r, LuaInteger.class);
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
    throwIllegalStateExceptionIfConvertFail(index, r, LuaNumber.class);
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
    throwIllegalStateExceptionIfConvertFail(index, r, LuaString.class);
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

  void applyUnaryOperator(
      Predicate<LuaValue> isTypeMatchRawOperator,
      Function<LuaValue, ? extends LuaValue> operator,
      LuaString metaMethodName) {
    LuaValue luaValue = this.popLuaValue();
    if (isTypeMatchRawOperator.test(luaValue)) {
      LuaValue result = operator.apply(luaValue);
      this.pushLuaValue(result);
      return;
    }
    if (this.existsMetaMethod(metaMethodName, luaValue)) {
      LuaValue result = this.callMetaMethod(metaMethodName, luaValue);
      this.pushLuaValue(result);
      return;
    }
    throw new UnsupportedOperationException("not found meta method of " + luaValue);
  }

  void applyBinaryOperator(
      BiPredicate<LuaValue, LuaValue> isTypeMatchRawOperator,
      BiFunction<LuaValue, LuaValue, ? extends LuaValue> operator,
      LuaString metaMethodName) {
    // 先pop b，再pop a
    var b = this.popLuaValue();
    var a = this.popLuaValue();
    if (isTypeMatchRawOperator.test(a, b)) {
      LuaValue result = operator.apply(a, b);
      assert result != null;
      this.pushLuaValue(result);
      return;
    }
    if (this.existsMetaMethod(metaMethodName, a, b)) {
      LuaValue result = this.callMetaMethod(metaMethodName, a, b);
      assert result != null;
      this.pushLuaValue(result);
      return;
    }
    throw new UnsupportedOperationException("not found meta method of " + a + " and " + b);
  }

  @Override
  public void arithmetic(ArithmeticOperator operator) {
    if (ArithmeticOperator.LUA_OPUNM.equals(operator)) {
      // 一元运算
      this.applyUnaryOperator(
          a -> operator.canApply(a, a),
          luaValue -> ArithmeticOperator.LUA_OPUNM.getOperator().apply(luaValue, null),
          ArithmeticOperator.LUA_OPUNM.getMetaMethodName());
    } else {
      this.applyBinaryOperator(
          operator::canApply, operator.getOperator(), operator.getMetaMethodName());
    }
  }

  @Override
  public void bitwise(BitwiseOperator operator) {
    if (BitwiseOperator.LUA_OPBNOT.equals(operator)) {
      // 一元运算
      this.applyUnaryOperator(
          a -> operator.canApply(a, a),
          luaValue -> BitwiseOperator.LUA_OPBNOT.getOperator().apply(luaValue, null),
          BitwiseOperator.LUA_OPBNOT.getMetaMethodName());
    } else {
      this.applyBinaryOperator(
          operator::canApply, operator.getOperator(), operator.getMetaMethodName());
    }
  }

  @Override
  public LuaBoolean compare(int index1, int index2, ComparisonOperator operator) {
    final var a = this.getLuaValue(index1);
    final var b = this.getLuaValue(index2);

    final LuaValue result;
    // 触发元方法
    if (LUA_OPEQ.equals(operator)) {
      // 等于 ==
      if (Comparison.isEqualsTypeMatch(a, b)) {
        result = operator.getOperator().apply(a, b);
      } else {
        if (this.existsMetaMethod(LUA_OPEQ.getMetaMethodName(), a, b)) {
          result = this.callMetaMethod(LUA_OPEQ.getMetaMethodName(), a, b);
        } else {
          return LuaValue.of(a.equals(b));
        }
      }
    } else if (LUA_OPLT.equals(operator)) {
      // 小于 <
      if (Comparison.isLessThanTypeMatch(a, b)) {
        result = operator.getOperator().apply(a, b);
      } else {
        result = this.callMetaMethod(LUA_OPLT.getMetaMethodName(), a, b);
      }
    } else if (LUA_OPLE.equals(operator)) {
      // 小于等于 <=
      if (Comparison.isLessThanOrEqualsTypeMatch(a, b)) {
        result = operator.getOperator().apply(a, b);
      } else {
        // 如果Lua找不到 __le 元方法，则会尝试调用 __lt 元方法
        if (this.existsMetaMethod(LUA_OPLE.getMetaMethodName(), a, b)) {
          // __le 元方法
          result = this.callMetaMethod(LUA_OPLE.getMetaMethodName(), a, b);
        } else if (this.existsMetaMethod(LUA_OPLT.getMetaMethodName(), a, b)) {
          // __lt 元方法
          result = this.callMetaMethod(LUA_OPLT.getMetaMethodName(), a, b);
        } else {
          throw new IllegalStateException(
              "cannot find meta method of operator " + operator + ", a = " + a + ", b = " + b);
        }
      }
    } else {
      throw new IllegalArgumentException("comparison operator " + operator);
    }
    return LuaBoolean.from(result);
  }

  @Override
  public void len(int index) {
    var a = this.getLuaValue(index);
    if (a instanceof LuaString) {
      var len = Length.length(a);
      this.pushLuaValue(len);
      return;
    }

    // 元方法
    if (this.existsMetaMethod(MetaMethod.LEN, a)) {
      var len = this.callMetaMethod(MetaMethod.LEN, a);
      this.pushLuaValue(len);
      return;
    }

    // 表
    if (a instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) a;
      var len = luaTable.length();
      this.pushLuaValue(len);
      return;
    }

    throw new IllegalStateException("cannot resolve length of " + a);
  }

  @Override
  public void concat(int n) {
    if (0 == n) {
      this.pushLuaValue(LuaValue.of(""));
    } else {
      for (; n >= 2; n--) {
        this.applyBinaryOperator(StringConcat::canConcat, StringConcat::concat, MetaMethod.CONCAT);
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

  LuaType rawGetTable(LuaValue table, LuaValue key) {
    LuaTable luaTable = (LuaTable) table;
    LuaValue result = luaTable.get(key);
    this.pushLuaValue(result);
    return luaTable.type();
  }

  /**
   * 获取 table[key]
   * <p>
   * 如果 table 不是表，或者 key 在表中不存在，就会触发 __index 元方法
   * <p>
   * __index 元方法既可以是函数，也可以是表
   */
  LuaType getTable(LuaValue table, LuaValue key) {
    // table 是表 并且 key 在表中存在
    if (LuaType.LUA_TTABLE.equals(table.type())) {
      LuaTable luaTable = (LuaTable) table;
      if (luaTable.containsKey(key)) {
        return this.rawGetTable(table, key);
      }
      if (!this.existsMetaMethod(MetaMethod.INDEX, luaTable)) {
        return this.rawGetTable(table, key);
      }
    }

    final LuaValue metaField = this.getMetaFieldInMetaTable(MetaMethod.INDEX, key);
    if (LuaType.LUA_TFUNCTION.equals(metaField.type())) {
      // 触发 __index 元方法
      LuaValue result = this.callMetaMethod(MetaMethod.INDEX, table, key);
      this.pushLuaValue(result);
      return result.type();
    } else if (LuaType.LUA_TTABLE.equals(metaField.type())) {
      // 找到的是 table
      return this.getTable(metaField, key);
    } else {
      throw new IllegalStateException(
          "meta field '__index' is not a function or table, it is " + metaField);
    }
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

  /**
   * 不处理元方法
   */
  void setTableWithoutResolveMetaMethod(LuaValue table, LuaValue key, LuaValue value) {
    LuaTable luaTable = (LuaTable) table;
    luaTable.put(key, value);
  }

  void setTable(LuaValue table, LuaValue key, LuaValue value) {
    // table 是表 并且 key 在表中存在
    if (LuaType.LUA_TTABLE.equals(table.type())) {
      LuaTable luaTable = (LuaTable) table;
      if (luaTable.containsKey(key)) {
        this.setTableWithoutResolveMetaMethod(table, key, value);
        return;
      }
      if (!this.existsMetaMethod(MetaMethod.NEWINDEX, luaTable)) {
        this.setTableWithoutResolveMetaMethod(table, key, value);
        return;
      }
    }

    final LuaValue metaField = this.getMetaFieldInMetaTable(MetaMethod.NEWINDEX, key);
    if (LuaType.LUA_TFUNCTION.equals(metaField.type())) {
      // 触发 __newindex 元方法
      this.callMetaMethodWithoutReturn(MetaMethod.NEWINDEX, table, key, value);
    } else if (LuaType.LUA_TTABLE.equals(metaField.type())) {
      // 找到的是 table
      this.setTable(metaField, key, value);
    } else {
      throw new IllegalStateException(
          "meta field '__index' is not a function or table, it is " + metaField);
    }
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
      luaClosure = LuaClosure.newPrototypeLuaClosure(prototype);
      luaClosure.setLuaUpvalue(0, LuaUpvalue.newFixedLuaUpvalue(env));
    } else {
      // 不需要管 Upvalue?
      throw new IllegalStateException("_ENV ?");
    }
    this.pushLuaValue(luaClosure);
    return 0;
  }

  protected void runLuaClosure() {
    throw new UnsupportedOperationException("please implement it in subclass");
  }

  LuaClosure resolveLuaClosure(LuaValue luaValue) {
    if (luaValue instanceof LuaClosure) {
      return (LuaClosure) luaValue;
    } else {
      // 触发元方法 __call
      LuaValue metaField = this.getMetaFieldInMetaTable(MetaMethod.CALL, luaValue);
      if (metaField instanceof LuaClosure) {
        return (LuaClosure) metaField;
      } else {
        throw new IllegalStateException(
            "lua value "
                + luaValue
                + " is not a closure and it's meta method __call doesn't exist."
                + " meta field "
                + metaField);
      }
    }
  }

  @Override
  public CallFrame call(int nArgs, int numberOfResultsWanted) {
    var allArgs = this.callStack.topCallFrame().popNArgs(nArgs);
    LuaClosure luaClosure = this.resolveLuaClosure(this.popLuaValue());
    // 函数的返回值个数
    final int numberOfElementsFunctionReturned;
    final CallFrame calledFrame;
    if (luaClosure.isPrototype()) {
      this.callStack.pushCallFrameForPrototype(luaClosure, allArgs);
      this.runLuaClosure();
      calledFrame = this.callStack.popCallFrame();
      var nRegs = luaClosure.getPrototype().getRegisterCount();
      numberOfElementsFunctionReturned = calledFrame.getTop() - nRegs;
    } else if (luaClosure.isJavaFunction()) {
      this.callStack.pushCallFrameForJavaFunction(luaClosure, allArgs);
      numberOfElementsFunctionReturned = luaClosure.getJavaFunction().apply(this);
      calledFrame = this.callStack.popCallFrame();
    } else {
      throw new IllegalStateException("nothing to call in closure " + luaClosure);
    }

    // 把 被调帧 中 的结果，放入 当前帧
    if (0 == numberOfResultsWanted) {
      // do nothing
    } else if (numberOfResultsWanted > 0) {
      var results = calledFrame.popNArgs(numberOfElementsFunctionReturned);
      this.callStack.topCallFrame().check(numberOfResultsWanted);
      this.callStack.topCallFrame().pushN(results, numberOfResultsWanted);
    } else {
      // 想放入所有
      var results = calledFrame.popNArgs(numberOfElementsFunctionReturned);
      this.callStack.topCallFrame().check(numberOfElementsFunctionReturned);
      this.callStack.topCallFrame().pushN(results);
    }
    return calledFrame;
  }

  @Override
  public void pushJavaFunction(JavaFunction javaFunction) {
    LuaClosure luaClosure = LuaClosure.newJavaFunctionLuaClosure(javaFunction, 0);
    this.pushLuaValue(luaClosure);
  }

  @Override
  public boolean isJavaFunction(int index) {
    LuaValue luaValue = this.toLuaValue(index);
    if (!(luaValue instanceof LuaClosure)) {
      return false;
    }
    LuaClosure luaClosure = (LuaClosure) luaValue;
    return luaClosure.isJavaFunction();
  }

  @Override
  public JavaFunction toJavaFunction(int index) {
    LuaValue luaValue = this.toLuaValue(index);
    if (!(luaValue instanceof LuaClosure)) {
      return null;
    }
    LuaClosure luaClosure = (LuaClosure) luaValue;
    return luaClosure.getJavaFunction();
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
    this.setTableWithoutResolveMetaMethod(globalTable, name, value);
  }

  @Override
  public void register(LuaString name, JavaFunction javaFunction) {
    this.pushJavaFunction(javaFunction);
    this.setGlobal(name);
  }

  @Override
  public void pushJavaClosure(JavaFunction javaFunction, int n) {
    LuaClosure luaClosure = LuaClosure.newJavaFunctionLuaClosure(javaFunction, n);
    for (int i = n - 1; i >= 0; i--) {
      LuaValue luaValue = this.popLuaValue();
      LuaUpvalue luaUpvalue = LuaUpvalue.newFixedLuaUpvalue(luaValue);
      luaClosure.setLuaUpvalue(i, luaUpvalue);
    }
    this.pushLuaValue(luaClosure);
  }

  /**
   * 非{@link LuaTable}，在注册表中，放它对应的元表，
   * <p>
   * 为了保证相同类型的数据，元表一样，需要对key的生产定一个规则
   *
   * @return 对应的key，字符串 _MT + type
   */
  static LuaString resolveKeyOfMetaTableInRegistry(LuaValue luaValue) {
    if (luaValue instanceof LuaTable) {
      throw new IllegalArgumentException("cannot be lua table. " + luaValue);
    }
    return LuaValue.of("_MT" + luaValue.type());
  }

  /**
   * 给 lua value设置元表
   *
   * @param metaTable 元表
   */
  void setMetaTable(LuaValue luaValue, LuaTable metaTable) {
    if (luaValue instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) luaValue;
      luaTable.setMetaTable(metaTable);
    } else {
      LuaString key = resolveKeyOfMetaTableInRegistry(luaValue);
      this.registry.put(key, metaTable);
    }
  }

  void removeMetaTable(LuaValue luaValue) {
    if (luaValue instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) luaValue;
      luaTable.removeMetaTable();
    } else {
      LuaString key = resolveKeyOfMetaTableInRegistry(luaValue);
      this.registry.remove(key);
    }
  }

  /**
   * @return true 如果对应的meta table存在
   */
  boolean existsMetaTableOf(LuaValue luaValue) {
    if (luaValue instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) luaValue;
      return luaTable.existsMetaTable();
    } else {
      LuaString key = resolveKeyOfMetaTableInRegistry(luaValue);
      return this.registry.containsKey(key);
    }
  }

  LuaTable getMetaTable(LuaValue luaValue) {
    if (!this.existsMetaTableOf(luaValue)) {
      throw new IllegalStateException("meta table of lua value " + luaValue + " doesn't exist");
    }
    if (luaValue instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) luaValue;
      return luaTable.getMetaTable();
    } else {
      LuaString key = resolveKeyOfMetaTableInRegistry(luaValue);
      return (LuaTable) this.registry.get(key);
    }
  }

  boolean existsMetaFieldInMetaTable(LuaString fieldName, LuaValue luaValue) {
    if (!this.existsMetaTableOf(luaValue)) {
      return false;
    }
    var metaTable = this.getMetaTable(luaValue);
    return metaTable.containsKey(fieldName);
  }

  /**
   * page 210
   * <p>
   * 对应书里的 getMetafield
   * <p>
   * getmetatable(luaValue)[fieldName]
   */
  LuaValue getMetaFieldInMetaTable(LuaString fieldName, LuaValue luaValue) {
    var metaTable = this.getMetaTable(luaValue);
    return metaTable.get(fieldName);
  }

  LuaValue getMetaFieldInMetaTable(LuaString fieldName, LuaValue a, LuaValue b) {
    LuaValue metaFieldInA = this.getMetaFieldInMetaTable(fieldName, a);
    if (!metaFieldInA.isLuaNil()) {
      return metaFieldInA;
    }
    LuaValue metaFieldInB = this.getMetaFieldInMetaTable(fieldName, b);
    if (!metaFieldInB.isLuaNil()) {
      return metaFieldInB;
    }
    return LuaValue.NIL;
  }

  boolean existsMetaMethod(LuaString metaMethodName, LuaValue luaValue) {
    if (!this.existsMetaFieldInMetaTable(metaMethodName, luaValue)) {
      return false;
    }
    LuaValue metaMethod = this.getMetaFieldInMetaTable(metaMethodName, luaValue);
    return !metaMethod.isLuaNil();
  }

  boolean existsMetaMethod(LuaString metaMethodName, LuaValue a, LuaValue b) {
    if (this.existsMetaTableOf(a)) {
      return this.existsMetaFieldInMetaTable(metaMethodName, a);
    }
    if (this.existsMetaTableOf(b)) {
      return this.existsMetaFieldInMetaTable(metaMethodName, b);
    }
    return false;
  }

  /**
   * 调用元方法。
   * <p>
   * 多个参数，1个返回结果
   *
   * @param metaMethodName 元方法的名字
   * @return 元方法的返回结果
   */
  LuaValue callMetaMethod(LuaString metaMethodName, LuaValue... luaValues) {
    LuaValue metaMethod = this.getMetaFieldInMetaTable(metaMethodName, luaValues[0]);
    this.checkStack(1 + luaValues.length);
    this.pushLuaValue(metaMethod);
    for (LuaValue luaValue : luaValues) {
      this.pushLuaValue(luaValue);
    }
    this.call(luaValues.length, 1);
    return this.popLuaValue();
  }

  /**
   * 调用元方法。
   * <p>
   * 多个参数，0个返回结果
   *
   * @param metaMethodName 元方法的名字
   */
  void callMetaMethodWithoutReturn(LuaString metaMethodName, LuaValue... luaValues) {
    LuaValue metaMethod = this.getMetaFieldInMetaTable(metaMethodName, luaValues[0]);
    this.checkStack(1 + luaValues.length);
    this.pushLuaValue(metaMethod);
    for (LuaValue luaValue : luaValues) {
      this.pushLuaValue(luaValue);
    }
    this.call(luaValues.length, 0);
  }

  @Override
  public boolean getMetaTable(int index) {
    LuaValue luaValue = this.getLuaValue(index);
    if (this.existsMetaTableOf(luaValue)) {
      LuaTable metaTable = this.getMetaTable(luaValue);
      this.pushLuaValue(metaTable);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void setMetaTable(int index) {
    LuaValue luaValue = this.getLuaValue(index);
    LuaValue metaTable = this.popLuaValue();
    if (metaTable.isLuaNil()) {
      this.removeMetaTable(luaValue);
    } else if (metaTable instanceof LuaTable) {
      this.setMetaTable(luaValue, (LuaTable) metaTable);
    } else {
      throw new IllegalStateException("meta table " + metaTable);
    }
  }

  @Override
  public void rawLen(int index) {
    var a = this.getLuaValue(index);
    if (a instanceof LuaString) {
      var len = Length.length(a);
      this.pushLuaValue(len);
      return;
    }

    // 表
    if (a instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) a;
      var len = luaTable.length();
      this.pushLuaValue(len);
      return;
    }

    throw new IllegalStateException("cannot resolve length of " + a);
  }

  @Override
  public boolean rawEqual(int index1, int index2) {
    LuaValue a = this.getLuaValue(index1);
    LuaValue b = this.getLuaValue(index2);
    return a.equals(b);
  }

  @Override
  public LuaType rawGet(int index) {
    LuaValue table = this.getLuaValue(index);
    LuaValue key = this.popLuaValue();
    return this.rawGetTable(table, key);
  }

  @Override
  public void rawSet(int index) {
    LuaValue table = this.getLuaValue(index);
    LuaValue value = this.popLuaValue();
    LuaValue key = this.popLuaValue();
    this.setTableWithoutResolveMetaMethod(table, key, value);
  }

  @Override
  public LuaType rawGetI(int index, LuaInteger i) {
    var table = this.getLuaValue(index);
    return this.rawGetTable(table, i);
  }

  @Override
  public void rawSetI(int index, LuaInteger i) {
    var table = this.getLuaValue(index);
    var value = this.popLuaValue();
    this.setTableWithoutResolveMetaMethod(table, i, value);
  }

  @Override
  public boolean next(int index) {
    LuaValue luaValue = this.getLuaValue(index);
    if (!(luaValue instanceof LuaTable)) {
      throw new IllegalStateException(
          "expected a table in index " + index + " but get " + luaValue);
    }
    LuaTable luaTable = (LuaTable) luaValue;
    LuaValue key = this.popLuaValue();
    LuaValue nextKey = luaTable.nextKey(key);
    if (nextKey.isLuaNil()) {
      // 遍历结束
      return false;
    } else {
      LuaValue nextValue = luaTable.get(nextKey);
      this.pushLuaValue(nextKey);
      this.pushLuaValue(nextValue);
      return true;
    }
  }

  @Override
  public int error() {
    var err = this.popLuaValue();
    throw new LuaErrorRuntimeException(err);
  }

  @Override
  public ThreadStatus pcall(int nArgs, int nResults, int msgh) {
    final CallFrame callerCallFrame = this.callStack.topCallFrame();
    try {
      this.call(nArgs, nResults);
      return ThreadStatus.LUA_OK;
    } catch (Exception e) {
      // 碰到异常时，调用栈 需要修复
      while (!callerCallFrame.equals(this.callStack.topCallFrame())) {
        this.callStack.popCallFrame();
      }
      // 把异常信息放入
      if (e instanceof LuaErrorRuntimeException) {
        LuaValue exceptionMessage = ((LuaErrorRuntimeException) e).getLuaValue();
        this.pushLuaValue(exceptionMessage);
      } else {
        // 不是 error 函数抛出的异常
        LuaString exceptionMessage = LuaValue.of(e.getMessage());
        this.pushLuaValue(exceptionMessage);
      }
      return ThreadStatus.LUA_ERRRUN;
    }
  }
}
