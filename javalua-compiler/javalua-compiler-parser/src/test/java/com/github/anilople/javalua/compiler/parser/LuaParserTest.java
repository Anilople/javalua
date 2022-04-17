package com.github.anilople.javalua.compiler.parser;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.Args.ExpListArgs;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.Var;
import com.github.anilople.javalua.compiler.ast.Var.NameVar;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.VarPrefixExp;
import com.github.anilople.javalua.compiler.ast.stat.FunctionCall;
import com.github.anilople.javalua.compiler.ast.stat.NoNameFunctionCall;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import constant.ResourceContentConstants.ch02;
import constant.ResourceContentConstants.ch16;
import io.LuaTestResource;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.LuaTestResourceUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxq
 */
class LuaParserTest {

  @Test
  void parseHelloWorld() {
    Block block = LuaParser.parse(ch02.hello_world.getLuaCode());
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
  void sum_stack_over_flow() {
    Block block = LuaParser.parse(ch16.sum_stack_over_flow.getLuaCode());
  }

  @Test
  void localVariable() {
    LuaParser.parse("local a = 1");
  }
}