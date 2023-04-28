package com.github.anilople.javalua.compiler.codegen;

/**
 * 提交指令
 *
 * @author wxq
 */
public interface InstructionEmitter {

  // r[a] = r[b]
  void emitMove(int a, int b);

  // r[a], r[a+1], ..., r[a+b] = nil
  void emitLoadNil(int a, int n);

  // r[a] = (bool)b; if (c) pc++
  void emitLoadBool(int a, int b, int c);

  // r[a] = kst[bx]
  void emitLoadK(int a);

  // r[a], r[a+1], ..., r[a+b-2] = vararg
  void emitVararg(int a, int n);

  // r[a] = emitClosure(proto[bx])
  void emitClosure(int a, int bx);

  // r[a] = {}
  void emitNewTable(int a, int nArr, int nRec);

  // r[a][(c-1)*FPF+i] := r[a+i], 1 <= i <= b
  void emitSetList(int a, int b, int c);

  // r[a] := r[b][rk(c)]
  void emitGetTable(int a, int b, int c);

  // r[a][rk(b)] = rk(c)
  void emitSetTable(int a, int b, int c);

  // r[a] = upval[b]
  void emitGetUpval(int a, int b);

  // upval[b] = r[a]
  void emitSetUpval(int a, int b);

  // r[a] = upval[b][rk(c)]
  void emitGetTabUp(int a, int b, int c);

  // upval[a][rk(b)] = rk(c)
  void emitSetTabUp(int a, int b, int c);

  // r[a], ..., r[a+c-2] = r[a](r[a+1], ..., r[a+b-1])
  void emitCall(int a, int nArgs, int nRet);

  // return r[a](r[a+1], ... ,r[a+b-1])
  void emitTailCall(int a, int nArgs);

  // return r[a], ... ,r[a+b-2]
  void emitReturn(int a, int n);

  // r[a+1] := r[b]; r[a] := r[b][rk(c)]
  void emitSelf(int a, int b, int c);

  // pc+=sBx; if (int a) close all upvalues >= r[a - 1]
  int emitJmp(int a, int sBx);

  // if not (r[a] <=> c) then pc++
  void emitTest(int a, int c);

  // if (r[b] <=> c) then r[a] := r[b] else pc++
  void emitTestSet(int a, int b, int c);

  int emitForPrep(int a, int sBx);

  int emitForLoop(int a, int sBx);

  void emitTForCall(int a, int c);

  void emitTForLoop(int a, int sBx);

  // r[a] = op r[b]
  void emitUnaryOp(int op, int a, int b);

  // r[a] = rk[b] op rk[c]
  // arith & bitwise & relational
  void emitBinaryOp(int op, int a, int b, int c);

}
