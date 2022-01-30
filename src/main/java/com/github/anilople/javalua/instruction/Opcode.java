package com.github.anilople.javalua.instruction;

import static com.github.anilople.javalua.instruction.OpArgMask.*;
import static com.github.anilople.javalua.instruction.OpMode.*;

import com.github.anilople.javalua.util.ByteUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * 操作码
 * <p>
 * 低6个bit
 *
 * @author wxq
 */
@Data
public final class Opcode {

  public static final Opcode MOVE = new Opcode(0, 1, OpArgR, OpArgN, iABC, "MOVE");
  public static final Opcode LOADK = new Opcode(0, 1, OpArgK, OpArgN, iABx, "LOADK");
  public static final Opcode LOADKX = new Opcode(0, 1, OpArgN, OpArgN, iABx, "LOADKX");
  public static final Opcode LOADBOOL = new Opcode(0, 1, OpArgU, OpArgU, iABC, "LOADBOOL");
  public static final Opcode LOADNIL = new Opcode(0, 1, OpArgU, OpArgN, iABC, "LOADNIL");
  public static final Opcode GETUPVAL = new Opcode(0, 1, OpArgU, OpArgN, iABC, "GETUPVAL");
  public static final Opcode GETTABUP = new Opcode(0, 1, OpArgU, OpArgK, iABC, "GETTABUP");
  public static final Opcode GETTABLE = new Opcode(0, 1, OpArgR, OpArgK, iABC, "GETTABLE");
  public static final Opcode SETTABUP = new Opcode(0, 0, OpArgK, OpArgK, iABC, "SETTABUP");
  public static final Opcode SETUPVAL = new Opcode(0, 0, OpArgU, OpArgN, iABC, "SETUPVAL");
  public static final Opcode SETTABLE = new Opcode(0, 0, OpArgK, OpArgK, iABC, "SETTABLE");
  public static final Opcode NEWTABLE = new Opcode(0, 1, OpArgU, OpArgU, iABC, "NEWTABLE");
  public static final Opcode SELF = new Opcode(0, 1, OpArgR, OpArgK, iABC, "SELF");
  public static final Opcode ADD = new Opcode(0, 1, OpArgK, OpArgK, iABC, "ADD");
  public static final Opcode SUB = new Opcode(0, 1, OpArgK, OpArgK, iABC, "SUB");
  public static final Opcode MUL = new Opcode(0, 1, OpArgK, OpArgK, iABC, "MUL");
  public static final Opcode MOD = new Opcode(0, 1, OpArgK, OpArgK, iABC, "MOD");
  public static final Opcode POW = new Opcode(0, 1, OpArgK, OpArgK, iABC, "POW");
  public static final Opcode DIV = new Opcode(0, 1, OpArgK, OpArgK, iABC, "DIV");
  public static final Opcode IDIV = new Opcode(0, 1, OpArgK, OpArgK, iABC, "IDIV");
  public static final Opcode BAND = new Opcode(0, 1, OpArgK, OpArgK, iABC, "BAND");
  public static final Opcode BOR = new Opcode(0, 1, OpArgK, OpArgK, iABC, "BOR");
  public static final Opcode BXOR = new Opcode(0, 1, OpArgK, OpArgK, iABC, "BXOR");
  public static final Opcode SHL = new Opcode(0, 1, OpArgK, OpArgK, iABC, "SHL");
  public static final Opcode SHR = new Opcode(0, 1, OpArgK, OpArgK, iABC, "SHR");
  public static final Opcode UNM = new Opcode(0, 1, OpArgR, OpArgN, iABC, "UNM");
  public static final Opcode BNOT = new Opcode(0, 1, OpArgR, OpArgN, iABC, "BNOT");
  public static final Opcode NOT = new Opcode(0, 1, OpArgR, OpArgN, iABC, "NOT");
  public static final Opcode LEN = new Opcode(0, 1, OpArgR, OpArgN, iABC, "LEN");
  public static final Opcode CONCAT = new Opcode(0, 1, OpArgR, OpArgR, iABC, "CONCAT");
  public static final Opcode JMP = new Opcode(0, 0, OpArgR, OpArgN, iAsBx, "JMP");
  public static final Opcode EQ = new Opcode(1, 0, OpArgK, OpArgK, iABC, "EQ");
  public static final Opcode LT = new Opcode(1, 0, OpArgK, OpArgK, iABC, "LT");
  public static final Opcode LE = new Opcode(1, 0, OpArgK, OpArgK, iABC, "LE");
  public static final Opcode TEST = new Opcode(1, 0, OpArgN, OpArgU, iABC, "TEST");
  public static final Opcode TESTSET = new Opcode(1, 1, OpArgR, OpArgU, iABC, "TESTSET");
  public static final Opcode CALL = new Opcode(0, 1, OpArgU, OpArgU, iABC, "CALL");
  public static final Opcode TAILCALL = new Opcode(0, 1, OpArgU, OpArgU, iABC, "TAILCALL");
  public static final Opcode RETURN = new Opcode(0, 0, OpArgU, OpArgN, iABC, "RETURN");
  public static final Opcode FORLOOP = new Opcode(0, 1, OpArgR, OpArgN, iAsBx, "FORLOOP");
  public static final Opcode FORPREP = new Opcode(0, 1, OpArgR, OpArgN, iAsBx, "FORPREP");
  public static final Opcode TFORCALL = new Opcode(0, 0, OpArgN, OpArgU, iABC, "TFORCALL");
  public static final Opcode TFORLOOP = new Opcode(0, 1, OpArgR, OpArgN, iAsBx, "TFORLOOP");
  public static final Opcode SETLIST = new Opcode(0, 0, OpArgU, OpArgU, iABC, "SETLIST");
  public static final Opcode CLOSURE = new Opcode(0, 1, OpArgU, OpArgN, iABx, "CLOSURE");
  public static final Opcode VARARG = new Opcode(0, 1, OpArgU, OpArgN, iABC, "VARARG");
  public static final Opcode EXTRAARG = new Opcode(0, 0, OpArgU, OpArgU, iAx, "EXTRAARG");

  static final List<Opcode> OPCODES =
      Collections.unmodifiableList(
          Arrays.asList(
              MOVE, LOADK, LOADKX, LOADBOOL, LOADNIL, GETUPVAL, GETTABUP, GETTABLE, SETTABUP,
              SETUPVAL, SETTABLE, NEWTABLE, SELF, ADD, SUB, MUL, MOD, POW, DIV, IDIV, BAND, BOR,
              BXOR, SHL, SHR, UNM, BNOT, NOT, LEN, CONCAT, JMP, EQ, LT, LE, TEST, TESTSET, CALL,
              TAILCALL, RETURN, FORLOOP, FORPREP, TFORCALL, TFORLOOP, SETLIST, CLOSURE, VARARG,
              EXTRAARG));

  private final OpMode opMode;
  private final OpArgMask c;
  private final OpArgMask b;
  private final boolean a;
  private final boolean t;
  private final String name;

  /**
   * 仿照官方源码的 opmode
   * <p/>
   * #define opmode(t,a,b,c,m) (((t)<<7) | ((a)<<6) | ((b)<<4) | ((c)<<2) | (m))
   */
  public Opcode(int t, int a, OpArgMask b, OpArgMask c, OpMode opMode, String name) {
    this.t = t > 0;
    this.a = a > 0;
    this.b = b;
    this.c = c;
    this.opMode = opMode;
    this.name = name;
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
