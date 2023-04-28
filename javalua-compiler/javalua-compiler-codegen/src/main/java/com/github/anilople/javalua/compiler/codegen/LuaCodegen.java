package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.ParList.VarargParList;
import com.github.anilople.javalua.compiler.ast.exp.FunctionDefExp;
import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import java.util.Optional;

import static com.github.anilople.javalua.compiler.codegen.LuaExpCodegen.cgFunctionDefExp;

/**
 * @author wxq
 */
public class LuaCodegen {

  public static Prototype convert(Block chunk) {
    FuncBody funcBody = new FuncBody(
        chunk.getLocation(),
        Optional.of(new VarargParList(new VarargExp(LuaAstLocation.EMPTY))),
        chunk
    );

    FunctionDefExp functionDefExp = new FunctionDefExp(LuaAstLocation.EMPTY, funcBody);

    FunctionInfo functionInfo = FunctionInfo.newFunctionInfo(null, functionDefExp);
    functionInfo.addLocalVariable("_ENV");
    cgFunctionDefExp(functionInfo, functionDefExp);
    return convert(functionInfo.getSubFunctionInfo(0));
  }

  static Prototype convert(FunctionInfo functionInfo) {
    return null;
  }
}
