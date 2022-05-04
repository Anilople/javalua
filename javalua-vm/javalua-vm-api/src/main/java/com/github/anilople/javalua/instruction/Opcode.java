package com.github.anilople.javalua.instruction;

import static com.github.anilople.javalua.instruction.OpArgMask.*;
import static com.github.anilople.javalua.instruction.OpMode.*;

import com.github.anilople.javalua.util.ByteUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 操作码
 * <p>
 * 低6个bit
 *
 * @author wxq
 */
public enum Opcode {
  MOVE(0, 1, OpArgR, OpArgN, iABC),
  LOADK(0, 1, OpArgK, OpArgN, iABx),
  LOADKX(0, 1, OpArgN, OpArgN, iABx),
  LOADBOOL(0, 1, OpArgU, OpArgU, iABC),
  LOADNIL(0, 1, OpArgU, OpArgN, iABC),
  GETUPVAL(0, 1, OpArgU, OpArgN, iABC),
  GETTABUP(0, 1, OpArgU, OpArgK, iABC),
  GETTABLE(0, 1, OpArgR, OpArgK, iABC),
  SETTABUP(0, 0, OpArgK, OpArgK, iABC),
  SETUPVAL(0, 0, OpArgU, OpArgN, iABC),
  SETTABLE(0, 0, OpArgK, OpArgK, iABC),
  NEWTABLE(0, 1, OpArgU, OpArgU, iABC),
  SELF(0, 1, OpArgR, OpArgK, iABC),
  ADD(0, 1, OpArgK, OpArgK, iABC),
  SUB(0, 1, OpArgK, OpArgK, iABC),
  MUL(0, 1, OpArgK, OpArgK, iABC),
  MOD(0, 1, OpArgK, OpArgK, iABC),
  POW(0, 1, OpArgK, OpArgK, iABC),
  DIV(0, 1, OpArgK, OpArgK, iABC),
  IDIV(0, 1, OpArgK, OpArgK, iABC),
  BAND(0, 1, OpArgK, OpArgK, iABC),
  BOR(0, 1, OpArgK, OpArgK, iABC),
  BXOR(0, 1, OpArgK, OpArgK, iABC),
  SHL(0, 1, OpArgK, OpArgK, iABC),
  SHR(0, 1, OpArgK, OpArgK, iABC),
  UNM(0, 1, OpArgR, OpArgN, iABC),
  BNOT(0, 1, OpArgR, OpArgN, iABC),
  NOT(0, 1, OpArgR, OpArgN, iABC),
  LEN(0, 1, OpArgR, OpArgN, iABC),
  CONCAT(0, 1, OpArgR, OpArgR, iABC),
  JMP(0, 0, OpArgR, OpArgN, iAsBx),
  EQ(1, 0, OpArgK, OpArgK, iABC),
  LT(1, 0, OpArgK, OpArgK, iABC),
  LE(1, 0, OpArgK, OpArgK, iABC),
  TEST(1, 0, OpArgN, OpArgU, iABC),
  TESTSET(1, 1, OpArgR, OpArgU, iABC),
  CALL(0, 1, OpArgU, OpArgU, iABC),
  TAILCALL(0, 1, OpArgU, OpArgU, iABC),
  RETURN(0, 0, OpArgU, OpArgN, iABC),
  FORLOOP(0, 1, OpArgR, OpArgN, iAsBx),
  FORPREP(0, 1, OpArgR, OpArgN, iAsBx),
  TFORCALL(0, 0, OpArgN, OpArgU, iABC),
  TFORLOOP(0, 1, OpArgR, OpArgN, iAsBx),
  SETLIST(0, 0, OpArgU, OpArgU, iABC),
  CLOSURE(0, 1, OpArgU, OpArgN, iABx),
  VARARG(0, 1, OpArgU, OpArgN, iABC),
  EXTRAARG(0, 0, OpArgU, OpArgU, iAx),
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

  /**
   * 仿照官方源码的 opmode
   * <p/>
   * #define opmode(t,a,b,c,m) (((t)<<7) | ((a)<<6) | ((b)<<4) | ((c)<<2) | (m))
   */
  Opcode(int t, int a, OpArgMask b, OpArgMask c, OpMode opMode) {
    this.t = t > 0;
    this.a = a > 0;
    this.b = b;
    this.c = c;
    this.opMode = opMode;
  }

  public String getName() {
    return this.name();
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
}
