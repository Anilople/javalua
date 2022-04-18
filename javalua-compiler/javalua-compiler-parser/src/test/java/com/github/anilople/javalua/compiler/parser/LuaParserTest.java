package com.github.anilople.javalua.compiler.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.anilople.javalua.compiler.ast.Args.ExpListArgs;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.ParList.NameListParList;
import com.github.anilople.javalua.compiler.ast.Var.NameVar;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.VarPrefixExp;
import com.github.anilople.javalua.compiler.ast.stat.FunctionDefineStat;
import com.github.anilople.javalua.compiler.ast.stat.LocalVarDeclStat;
import com.github.anilople.javalua.compiler.ast.stat.NoNameFunctionCall;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import constant.ResourceContentConstants.ch02;
import io.LuaTestResource;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.LuaTestResourceUtils;

/**
 * @author wxq
 */
class LuaParserTest {

  static Block parse(String luaCode) {
    return LuaParser.parse(luaCode);
  }

  @Test
  void parseHelloWorld() {
    Block block = parse(ch02.hello_world.getLuaCode());
    assertTrue(block.getOptionalRetstat().isEmpty());
    List<Stat> statList = block.getStatList();
    assertEquals(1, statList.size());
    Stat stat = statList.get(0);
    assertTrue(stat instanceof NoNameFunctionCall);

    NoNameFunctionCall functionCall = (NoNameFunctionCall) stat;

    // print
    VarPrefixExp varPrefixExp = (VarPrefixExp) functionCall.getPrefixExp();
    NameVar nameVar = (NameVar) varPrefixExp.getVar();
    Name print = nameVar.getName();
    assertEquals("print", print.getIdentifier());

    // ("Hello, World!")
    ExpListArgs expListArgs = (ExpListArgs) functionCall.getArgs();
    ExpList expList = expListArgs.getOptionalExpList().get();
    assertEquals(1, expList.size());
    LiteralStringExp literalStringExp = (LiteralStringExp) expList.getFirst();
    assertEquals("Hello, World!", literalStringExp.getContent());
  }

  @Test
  void parseAllLuaCode() throws IOException {
    List<LuaTestResource> luaTestResources = LuaTestResourceUtils.getLuaTestResources();
    System.out.println("luaTestResources size " + luaTestResources.size());
    for (LuaTestResource luaTestResource : luaTestResources) {
      try {
        LuaParser.parse(luaTestResource.getLuaCode(), luaTestResource.getLuaFilePath());
      } catch (Throwable e) {
        throw new IllegalStateException("parse " + luaTestResource.getLuaFilePath(), e);
      }
    }
  }

  @Test
  void localVariableAssign() {
    Block block = parse("local a = 1");
    assertTrue(block.getStatList().get(0) instanceof LocalVarDeclStat);
  }

  @Test
  void localVariables() {
    Block block = parse("local a,t,k,v,e;");
    LocalVarDeclStat localVarDeclStat = (LocalVarDeclStat) block.getStatList().get(0);
    NameList nameList = localVarDeclStat.getNamelist();
    assertEquals("a", nameList.get(0).getIdentifier());
    assertEquals("t", nameList.get(1).getIdentifier());
    assertEquals("k", nameList.get(2).getIdentifier());
    assertEquals("v", nameList.get(3).getIdentifier());
    assertEquals("e", nameList.get(4).getIdentifier());
  }

  @Test
  void tableAccess() {
    parse("v = t[k];");
  }

  @Test
  void tableSetValue() {
    parse("t[2] = 3");
  }

  @Test
  void arrayAccess() {
    parse("v = t[100]");
  }

  @Test
  void newTable() {
    parse("b = {x=1, y=2}");
  }

  @Test
  void function_max_with_semicolon() {
    Block block = parse("function max(a, b) if (a > b) then return a; else return b; end end");
    List<Stat> statList = block.getStatList();
    assertEquals(1, statList.size());
    FunctionDefineStat functionDefineStat = (FunctionDefineStat) statList.get(0);
    assertEquals("max", functionDefineStat.getFuncName().getName().getIdentifier());
    FuncBody funcBody = functionDefineStat.getFuncBody();
    NameListParList nameListParList = (NameListParList) funcBody.getOptionalParList().get();
    assertEquals(2, nameListParList.getNameList().size());
    assertEquals("a", nameListParList.getNameList().get(0).getIdentifier());
    assertEquals("b", nameListParList.getNameList().get(1).getIdentifier());

    //    IfStat ifStat = (IfStat) funcBody.getBlock().getStatList().get(0);
    //    BinopExp binopExp = (BinopExp) ifStat.getExp();
    //    assertEquals("a", ((LiteralStringExp)binopExp.getExp1()).getContent());
    //    assertEquals("b", ((LiteralStringExp)binopExp.getExp2()).getContent());
    //    Block thenBlock = ifStat.getBlock();
    //    assertTrue(thenBlock.getOptionalRetstat().isEmpty());
    //    assertTrue(thenBlock.getOptionalRetstat().isPresent());
    //    thenBlock.getOptionalRetstat().get();
  }

  @Test
  void if_then_assign() {
    parse("if val == nil then a = 1 end");
  }
}
