package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.RetStat;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.stat.*;

import static com.github.anilople.javalua.compiler.codegen.LuaBlockCodegen.cgBlock;
import static com.github.anilople.javalua.compiler.codegen.LuaExpCodegen.cgExp;

/**
 * @author wxq
 */
public class LuaStatCodegen {

  static void cgRetStat(FunctionInfo functionInfo, RetStat retStat) {
    if (retStat.getOptionalExpList().isEmpty()) {
      functionInfo.emitReturn(0, 0);
    }

    ExpList expList = retStat.getOptionalExpList().get();

    if (expList.size() == 1) {
      // 只 return 1个值
      Exp exp = expList.get(0);
//      if (exp instanceof Name)
    }

    // 需要 return 多个值
  }

  static void cgStat(FunctionInfo functionInfo, Stat node) {
    if (node instanceof GotoStat) {
      cgGotoStat(functionInfo, (GotoStat) node);
    }
    if (node instanceof LabelStat) {
      cgLabelStat(functionInfo, (LabelStat) node);
    }
    if (node instanceof LocalFunctionDefineStat) {
      cgLocalFunctionDefineStat(functionInfo, (LocalFunctionDefineStat) node);
    }
    if (node instanceof RepeatStat) {
      cgRepeatStat(functionInfo, (RepeatStat) node);
    }
    if (node instanceof EmptyStat) {
      cgEmptyStat(functionInfo, (EmptyStat) node);
    }
    if (node instanceof LocalVarDeclStat) {
      cgLocalVarDeclStat(functionInfo, (LocalVarDeclStat) node);
    }
    if (node instanceof AssignStat) {
      cgAssignStat(functionInfo, (AssignStat) node);
    }
    if (node instanceof NameFunctionCall) {
      cgNameFunctionCall(functionInfo, (NameFunctionCall) node);
    }
    if (node instanceof NoNameFunctionCall) {
      cgNoNameFunctionCall(functionInfo, (NoNameFunctionCall) node);
    }
    if (node instanceof BreakStat) {
      cgBreakStat(functionInfo, (BreakStat) node);
    }
    if (node instanceof WhileStat) {
      cgWhileStat(functionInfo, (WhileStat) node);
    }
    if (node instanceof DoStat) {
      cgDoStat(functionInfo, (DoStat) node);
    }
    if (node instanceof FunctionDefineStat) {
      cgFunctionDefineStat(functionInfo, (FunctionDefineStat) node);
    }
    if (node instanceof ForNumStat) {
      cgForNumStat(functionInfo, (ForNumStat) node);
    }
    if (node instanceof IfStat) {
      cgIfStat(functionInfo, (IfStat) node);
    }
    if (node instanceof ForInStat) {
      cgForInStat(functionInfo, (ForInStat) node);
    }

    throw new IllegalArgumentException("stat " + node);
  }

  static void cgGotoStat(FunctionInfo functionInfo, GotoStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgLabelStat(FunctionInfo functionInfo, LabelStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgLocalFunctionDefineStat(FunctionInfo functionInfo, LocalFunctionDefineStat node) {
    int index = functionInfo.addLocalVariable(node.getName().getIdentifier());
//    cgFunctionDefineExp(functionInfo, index);
    throw new UnsupportedOperationException();
  }

  static void cgRepeatStat(FunctionInfo functionInfo, RepeatStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgEmptyStat(FunctionInfo functionInfo, EmptyStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgLocalVarDeclStat(FunctionInfo functionInfo, LocalVarDeclStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgAssignStat(FunctionInfo functionInfo, AssignStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgNameFunctionCall(FunctionInfo functionInfo, NameFunctionCall node) {
    throw new UnsupportedOperationException();
  }

  static void cgNoNameFunctionCall(FunctionInfo functionInfo, NoNameFunctionCall node) {
    int r = functionInfo.allocRegister();
//    cgFunctionCallExp(functionInfo, node, r, 0);
    functionInfo.freeRegister();
    throw new UnsupportedOperationException();
  }

  static void cgBreakStat(FunctionInfo functionInfo, BreakStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgWhileStat(FunctionInfo functionInfo, WhileStat node) {
    final int pcBeforeExp = functionInfo.pc();
    int r = functionInfo.allocRegister();
//    cgExp(functionInfo, node.getExp(), r, 1);

    throw new UnsupportedOperationException();
  }

  static void cgDoStat(FunctionInfo functionInfo, DoStat node) {
    functionInfo.enterScope();
    cgBlock(functionInfo, node.getBlock());
    functionInfo.closeOpenUpvalues();
    functionInfo.exitScope();
  }

  static void cgFunctionDefineStat(FunctionInfo functionInfo, FunctionDefineStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgForNumStat(FunctionInfo functionInfo, ForNumStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgIfStat(FunctionInfo functionInfo, IfStat node) {
    throw new UnsupportedOperationException();
  }

  static void cgForInStat(FunctionInfo functionInfo, ForInStat node) {
    throw new UnsupportedOperationException();
  }


}
