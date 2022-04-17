package com.github.anilople.javalua.compiler.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.ParList.NameListParList;
import com.github.anilople.javalua.compiler.ast.exp.FunctionDefExp;
import com.github.anilople.javalua.compiler.ast.exp.IntegerExp;
import com.github.anilople.javalua.compiler.ast.stat.GotoStat;
import com.github.anilople.javalua.compiler.ast.stat.LabelStat;
import com.github.anilople.javalua.compiler.ast.stat.LocalFunctionDefineStat;
import com.github.anilople.javalua.compiler.ast.stat.LocalVarDeclStat;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaStatParserTest {

  static Stat parseStat(String luaCode) {
    LuaLexer lexer = LuaLexer.newLuaLexer(luaCode);
    return LuaStatParser.parseStat(lexer);
  }

  @Test
  void parseStatList() {
  }

  @Test
  void parseGotoStat() {
    GotoStat stat = (GotoStat) parseStat("goto a");
    assertEquals("a", stat.getName().getIdentifier());
  }

  @Test
  void parseLocalFunctionDefineStat() {
    LocalFunctionDefineStat stat =
        (LocalFunctionDefineStat) parseStat("local function add(a, b) return a + b end");
    assertEquals("add", stat.getName().getIdentifier());
    FuncBody funcBody = stat.getFuncbody();
    NameListParList nameListParList = (NameListParList) funcBody.getOptionalParList().get();
    NameList nameList = nameListParList.getNameList();
    assertEquals(2, nameList.size());
    assertEquals("a", nameList.get(0).getIdentifier());
    assertEquals("b", nameList.get(1).getIdentifier());
  }

  @Test
  void parseLabelStat() {
    LabelStat stat = (LabelStat) parseStat("::a::");
    assertEquals("a", stat.getName().getIdentifier());
  }

  @Test
  void parseRepeatStat() {
  }

  @Test
  void parseEmptyStat() {
  }

  @Test
  void parseLocalVarDeclStat() {
    LocalVarDeclStat localVarDeclStat = (LocalVarDeclStat) parseStat("local a = 1");
    assertEquals("a", localVarDeclStat.getNamelist().getFirst().getIdentifier());
    IntegerExp integerExp = (IntegerExp) localVarDeclStat.getOptionalExpList().get().getFirst();
    assertEquals(1, integerExp.getValue());
  }

  @Test
  void canParseFunctionCall() {
  }

  @Test
  void parseBreakStat() {
  }

  @Test
  void parseWhileStat() {
  }

  @Test
  void parseDoStat() {
  }

  @Test
  void parseFunctionDefineStat() {
  }

  @Test
  void parseIfStat() {
  }

  @Test
  void parseForInStat() {
  }

  @Test
  void parseForNumStat() {
  }

  @Test
  void canParseAssignStat() {
  }

  @Test
  void parseAssignStat() {
  }

  @Test
  void testParseAssignStat() {
  }

  @Test
  void parseForStat() {
  }

  @Test
  void parseLocalAssignOrFuncDefStat() {
  }

  @Test
  void parseAssignOrFunctionCallStat() {
  }
}
