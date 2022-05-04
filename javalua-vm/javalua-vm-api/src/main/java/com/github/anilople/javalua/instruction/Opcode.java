package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.util.ByteUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.anilople.javalua.instruction.OpArgMask.*;
import static com.github.anilople.javalua.instruction.OpMode.*;

/**
 * 操作码
 * <p>
 * 低6个bit
 *
 * @author wxq
 */
public enum Opcode {
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

}
