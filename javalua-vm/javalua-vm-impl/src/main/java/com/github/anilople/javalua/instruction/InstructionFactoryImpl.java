package com.github.anilople.javalua.instruction;

/**
 * @author wxq
 */
public class InstructionFactoryImpl implements InstructionFactory {

  @Override
  public Instruction getInstructionBy(int code) {
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
}
