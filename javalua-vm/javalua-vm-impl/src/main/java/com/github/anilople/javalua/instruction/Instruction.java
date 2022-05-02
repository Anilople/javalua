package com.github.anilople.javalua.instruction;

import static com.github.anilople.javalua.instruction.Instruction.Opcode.OpArgMask.*;
import static com.github.anilople.javalua.instruction.Instruction.Opcode.OpMode.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.Code;
import com.github.anilople.javalua.instruction.Instruction.Opcode.OpMode;
import com.github.anilople.javalua.util.ByteUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 指令
 * <p>
 * 在lua中，用4个byte表示
 *
 * @author wxq
 */
public interface Instruction {

  static Instruction of(int code) {
    Opcode opcode = Opcode.of(code);
    switch (opcode) {
      case MOVE:
        return new MOVE(code);
      case LOADK:
        return new LOADK(code);
      case LOADKX:
        return new LOADKX(code);
      case LOADBOOL:
        return new LOADBOOL(code);
      case LOADNIL:
        return new LOADNIL(code);
      case GETUPVAL:
        return new GETUPVAL(code);
      case GETTABUP:
        return new GETTABUP(code);
      case GETTABLE:
        return new GETTABLE(code);
      case SETTABUP:
        return new SETTABUP(code);
      case SETUPVAL:
        return new SETUPVAL(code);
      case SETTABLE:
        return new SETTABLE(code);
      case NEWTABLE:
        return new NEWTABLE(code);
      case SELF:
        return new SELF(code);
      case ADD:
        return new ADD(code);
      case SUB:
        return new SUB(code);
      case MUL:
        return new MUL(code);
      case MOD:
        return new MOD(code);
      case POW:
        return new POW(code);
      case DIV:
        return new DIV(code);
      case IDIV:
        return new IDIV(code);
      case BAND:
        return new BAND(code);
      case BOR:
        return new BOR(code);
      case BXOR:
        return new BXOR(code);
      case SHL:
        return new SHL(code);
      case SHR:
        return new SHR(code);
      case UNM:
        return new UNM(code);
      case BNOT:
        return new BNOT(code);
      case NOT:
        return new NOT(code);
      case LEN:
        return new LEN(code);
      case CONCAT:
        return new CONCAT(code);
      case JMP:
        return new JMP(code);
      case EQ:
        return new EQ(code);
      case LT:
        return new LT(code);
      case LE:
        return new LE(code);
      case TEST:
        return new TEST(code);
      case TESTSET:
        return new TESTSET(code);
      case CALL:
        return new CALL(code);
      case TAILCALL:
        return new TAILCALL(code);
      case RETURN:
        return new RETURN(code);
      case FORLOOP:
        return new FORLOOP(code);
      case FORPREP:
        return new FORPREP(code);
      case TFORCALL:
        return new TFORCALL(code);
      case TFORLOOP:
        return new TFORLOOP(code);
      case SETLIST:
        return new SETLIST(code);
      case CLOSURE:
        return new CLOSURE(code);
      case VARARG:
        return new VARARG(code);
      case EXTRAARG:
        return new EXTRAARG(code);
      default:
        throw new IllegalStateException("unknown op code " + opcode + " origin code value " + code);
    }
  }

  static Instruction[] convert(int[] code) {
    final int length = code.length;
    Instruction[] instructions = new Instruction[length];
    for (int i = 0; i < length; i++) {
      int codeValue = code[i];
      Instruction instruction = Instruction.of(codeValue);
      instructions[i] = instruction;
    }
    return instructions;
  }

  static Instruction[] convert(Code code) {
    return convert(code.getCode());
  }

  Opcode getOpcode();

  Operand getOperand();

  void applyTo(LuaVM luaVM);

  /**
   * 操作码
   * <p>
   * 低6个bit
   *
   * @author wxq
   */
  enum Opcode {
    MOVE(0, 1, OpArgR, OpArgN, iABC, MOVE.class),
    LOADK(0, 1, OpArgK, OpArgN, iABx, LOADK.class),
    LOADKX(0, 1, OpArgN, OpArgN, iABx, LOADKX.class),
    LOADBOOL(0, 1, OpArgU, OpArgU, iABC, LOADBOOL.class),
    LOADNIL(0, 1, OpArgU, OpArgN, iABC, LOADNIL.class),
    GETUPVAL(0, 1, OpArgU, OpArgN, iABC, GETUPVAL.class),
    GETTABUP(0, 1, OpArgU, OpArgK, iABC, GETTABUP.class),
    GETTABLE(0, 1, OpArgR, OpArgK, iABC, GETTABLE.class),
    SETTABUP(0, 0, OpArgK, OpArgK, iABC, SETTABUP.class),
    SETUPVAL(0, 0, OpArgU, OpArgN, iABC, SETUPVAL.class),
    SETTABLE(0, 0, OpArgK, OpArgK, iABC, SETTABLE.class),
    NEWTABLE(0, 1, OpArgU, OpArgU, iABC, NEWTABLE.class),
    SELF(0, 1, OpArgR, OpArgK, iABC, SELF.class),
    ADD(0, 1, OpArgK, OpArgK, iABC, ADD.class),
    SUB(0, 1, OpArgK, OpArgK, iABC, SUB.class),
    MUL(0, 1, OpArgK, OpArgK, iABC, MUL.class),
    MOD(0, 1, OpArgK, OpArgK, iABC, MOD.class),
    POW(0, 1, OpArgK, OpArgK, iABC, POW.class),
    DIV(0, 1, OpArgK, OpArgK, iABC, DIV.class),
    IDIV(0, 1, OpArgK, OpArgK, iABC, IDIV.class),
    BAND(0, 1, OpArgK, OpArgK, iABC, BAND.class),
    BOR(0, 1, OpArgK, OpArgK, iABC, BOR.class),
    BXOR(0, 1, OpArgK, OpArgK, iABC, BXOR.class),
    SHL(0, 1, OpArgK, OpArgK, iABC, SHL.class),
    SHR(0, 1, OpArgK, OpArgK, iABC, SHR.class),
    UNM(0, 1, OpArgR, OpArgN, iABC, UNM.class),
    BNOT(0, 1, OpArgR, OpArgN, iABC, BNOT.class),
    NOT(0, 1, OpArgR, OpArgN, iABC, NOT.class),
    LEN(0, 1, OpArgR, OpArgN, iABC, LEN.class),
    CONCAT(0, 1, OpArgR, OpArgR, iABC, CONCAT.class),
    JMP(0, 0, OpArgR, OpArgN, iAsBx, JMP.class),
    EQ(1, 0, OpArgK, OpArgK, iABC, EQ.class),
    LT(1, 0, OpArgK, OpArgK, iABC, LT.class),
    LE(1, 0, OpArgK, OpArgK, iABC, LE.class),
    TEST(1, 0, OpArgN, OpArgU, iABC, TEST.class),
    TESTSET(1, 1, OpArgR, OpArgU, iABC, TESTSET.class),
    CALL(0, 1, OpArgU, OpArgU, iABC, CALL.class),
    TAILCALL(0, 1, OpArgU, OpArgU, iABC, TAILCALL.class),
    RETURN(0, 0, OpArgU, OpArgN, iABC, RETURN.class),
    FORLOOP(0, 1, OpArgR, OpArgN, iAsBx, FORLOOP.class),
    FORPREP(0, 1, OpArgR, OpArgN, iAsBx, FORPREP.class),
    TFORCALL(0, 0, OpArgN, OpArgU, iABC, TFORCALL.class),
    TFORLOOP(0, 1, OpArgR, OpArgN, iAsBx, TFORLOOP.class),
    SETLIST(0, 0, OpArgU, OpArgU, iABC, SETLIST.class),
    CLOSURE(0, 1, OpArgU, OpArgN, iABx, CLOSURE.class),
    VARARG(0, 1, OpArgU, OpArgN, iABC, VARARG.class),
    EXTRAARG(0, 0, OpArgU, OpArgU, iAx, EXTRAARG.class),
    ;

    static final List<Opcode> OPCODES =
        Collections.unmodifiableList(
            Arrays.asList(
                MOVE, LOADK, LOADKX, LOADBOOL, LOADNIL, GETUPVAL, GETTABUP, GETTABLE, SETTABUP,
                SETUPVAL, SETTABLE, NEWTABLE, SELF, ADD, SUB, MUL, MOD, POW, DIV, IDIV, BAND, BOR,
                BXOR, SHL, SHR, UNM, BNOT, NOT, LEN, CONCAT, JMP, EQ, LT, LE, TEST, TESTSET, CALL,
                TAILCALL, RETURN, FORLOOP, FORPREP, TFORCALL, TFORLOOP, SETLIST, CLOSURE, VARARG,
                EXTRAARG));

    static int getOpcodeValueOf(Opcode opcode) {
      int size = OPCODES.size();
      for (int i = 0; i < size; i++) {
        if (OPCODES.get(i).equals(opcode)) {
          return i;
        }
      }
      throw new IllegalStateException("unknown " + opcode);
    }

    private final OpMode opMode;
    private final OpArgMask c;
    private final OpArgMask b;
    private final boolean a;
    private final boolean t;
    private final Class<? extends AbstractInstruction> instructionClass;

    /**
     * 仿照官方源码的 opmode
     * <p/>
     * #define opmode(t,a,b,c,m) (((t)<<7) | ((a)<<6) | ((b)<<4) | ((c)<<2) | (m))
     */
    Opcode(
        int t,
        int a,
        OpArgMask b,
        OpArgMask c,
        OpMode opMode,
        Class<? extends AbstractInstruction> instructionClass) {
      this.t = t > 0;
      this.a = a > 0;
      this.b = b;
      this.c = c;
      this.opMode = opMode;
      this.instructionClass = instructionClass;
    }

    public String getName() {
      return this.instructionClass.getSimpleName();
    }

    public OpMode getOpMode() {
      return this.opMode;
    }

    public OpArgMask getCArgMask() {
      return this.c;
    }

    public OpArgMask getBArgMask() {
      return this.b;
    }

    public boolean existsA() {
      return this.a;
    }

    public boolean isTest() {
      return this.t;
    }

    public static Opcode of(int code) {
      int index = ByteUtils.low6BitsOf(code);
      if (index > OPCODES.size()) {
        throw new IllegalArgumentException("code " + code + " index " + index);
      }
      return OPCODES.get(index);
    }

    /**
     * 指令编码模式
     *
     * @author wxq
     */
    public enum OpMode {
      iABC,
      iABx,
      iAsBx,
      iAx;
    }

    /**
     * 操作数的类型
     *
     * @author wxq
     */
    public enum OpArgMask {
      /**
       * 这个操作数不会被使用
       */
      OpArgN,
      /**
       * iABC模式下表示寄存器索引
       * <p/>
       * iAsBx模式下表示跳转偏移
       */
      OpArgR,
      /**
       * 操作数可能表示布尔值，整数值，upvalue索引，子函数索引等
       */
      OpArgU,
      /**
       * 表示常量索引或者寄存器索引
       */
      OpArgK,
      ;
    }
  }

  /**
   * 操作数
   * <p/>
   * 高26个bit
   * <p/>
   * 注意：Lua虚拟机指令操作数里携带的寄存器索引是从0开始的，而Lua API里的栈索引是从1开始的
   *
   * @author wxq
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lopcodes.h#L274">lopcodes.h#L274
   * OpArgMask</a>
   */
  final class Operand {

    /**
     * 2^18 - 1 = 262143
     */
    static final int MAXARG_Bx = (1 << 18) - 1;
    /**
     * 262143 / 2 = 131071
     */
    static final int MAXARG_sBx = MAXARG_Bx >> 1;

    private final int codeValue;

    private Operand(int codeValue) {
      this.codeValue = codeValue;
    }

    public int getCodeValue() {
      return this.codeValue;
    }

    public int A() {
      return A(this.codeValue);
    }

    public int B() {
      return B(this.codeValue);
    }

    public int C() {
      return C(this.codeValue);
    }

    /**
     * @return 0 至 262143
     */
    public int Bx() {
      return Bx(this.codeValue);
    }

    /**
     * {@link OpMode#iAsBx}模式下的sBx操作数会被解释成有符号整数，
     * <p/>
     * 其它情况下被解释成无符号整数
     * <p/>
     * page 43 44
     * <p/>
     * Lua虚拟机用偏移二进制码（Offset Binary，也叫作Excess-K）
     * @return -131071 至 131072
     */
    public int sBx() {
      var bx = ByteUtils.getBits(this.codeValue, 14, 18);
      return bx - MAXARG_sBx;
    }

    public int Ax() {
      return Ax(this.codeValue);
    }

    static int A(int code) {
      return ByteUtils.getBits(code, 6, 8);
    }

    static int B(int code) {
      return ByteUtils.getBits(code, 23, 9);
    }

    static int C(int code) {
      return ByteUtils.getBits(code, 14, 9);
    }

    static int Bx(int code) {
      return ByteUtils.getBits(code, 14, 18);
    }

    static int Ax(int code) {
      return ByteUtils.getBits(code, 6, 26);
    }

    static Operand of(int code) {
      return new Operand(code);
    }

    static Operand iABC(int B, int C, int A) {
      int value = B << 23;
      value |= C << 14;
      value |= A << 6;
      return Operand.of(value);
    }
  }
}
