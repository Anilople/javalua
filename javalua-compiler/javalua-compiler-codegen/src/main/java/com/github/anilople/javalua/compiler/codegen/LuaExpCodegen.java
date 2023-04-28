package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.*;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.*;

/**
 * @author wxq
 */
public class LuaExpCodegen {

  public static void cgExp(FunctionInfo functionInfo, Exp node) {
    if (node instanceof TableConstructorExp) {
      cgTableConstructorExp(functionInfo, (TableConstructorExp) node);
    }
    if (node instanceof FunctionDefExp) {
      cgFunctionDefExp(functionInfo, (FunctionDefExp) node);
    }
    if (node instanceof BinopExp) {
      cgBinopExp(functionInfo, (BinopExp) node);
    }
    if (node instanceof TrueExp) {
      cgTrueExp(functionInfo, (TrueExp) node);
    }
    if (node instanceof FloatExp) {
      cgFloatExp(functionInfo, (FloatExp) node);
    }
    if (node instanceof IntegerExp) {
      cgIntegerExp(functionInfo, (IntegerExp) node);
    }
    if (node instanceof ParenthesesPrefixExp) {
      cgParenthesesPrefixExp(functionInfo, (ParenthesesPrefixExp) node);
    }
    if (node instanceof VarPrefixExp) {
      cgVarPrefixExp(functionInfo, (VarPrefixExp) node);
    }
    if (node instanceof FunctionCallPrefixExp) {
      cgFunctionCallPrefixExp(functionInfo, (FunctionCallPrefixExp) node);
    }
    if (node instanceof LiteralStringExp) {
      cgLiteralStringExp(functionInfo, (LiteralStringExp) node);
    }
    if (node instanceof FalseExp) {
      cgFalseExp(functionInfo, (FalseExp) node);
    }
    if (node instanceof VarargExp) {
      cgVarargExp(functionInfo, (VarargExp) node);
    }
    if (node instanceof NilExp) {
      cgNilExp(functionInfo, (NilExp) node);
    }
    if (node instanceof UnopExp) {
      cgUnopExp(functionInfo, (UnopExp) node);
    }

  }

  static void cgTableConstructorExp(FunctionInfo functionInfo, TableConstructorExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgFunctionDefExp(FunctionInfo functionInfo, FunctionDefExp node) {
    cgFunctionDefExp(functionInfo, node, 0);
  }

  /**
   * f[a] := function(args) body end
   */
  static void cgFunctionDefExp(FunctionInfo functionInfo, FunctionDefExp node, int a) {
    FunctionInfo subFunctionInfo = FunctionInfo.newFunctionInfo(functionInfo, node);
    throw new UnsupportedOperationException();
  }

  static void cgBinopExp(FunctionInfo functionInfo, BinopExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgTrueExp(FunctionInfo functionInfo, TrueExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgFloatExp(FunctionInfo functionInfo, FloatExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgIntegerExp(FunctionInfo functionInfo, IntegerExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgParenthesesPrefixExp(FunctionInfo functionInfo, ParenthesesPrefixExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgVarPrefixExp(FunctionInfo functionInfo, VarPrefixExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgFunctionCallPrefixExp(FunctionInfo functionInfo, FunctionCallPrefixExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgLiteralStringExp(FunctionInfo functionInfo, LiteralStringExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgFalseExp(FunctionInfo functionInfo, FalseExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgVarargExp(FunctionInfo functionInfo, VarargExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgNilExp(FunctionInfo functionInfo, NilExp node) {
    throw new UnsupportedOperationException();
  }

  static void cgUnopExp(FunctionInfo functionInfo, UnopExp node) {
    throw new UnsupportedOperationException();
  }


}
