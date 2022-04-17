package com.github.anilople.javalua.compiler.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.compiler.ast.exp.IntegerExp;
import com.github.anilople.javalua.compiler.ast.stat.LocalVarDeclStat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaStatParserTest {

  @Test
  void parseLocalVarDeclStat() {
    LuaLexer lexer = LuaLexer.newLuaLexer("local a = 1");
    LocalVarDeclStat localVarDeclStat = LuaStatParser.parseLocalVarDeclStat(lexer);
    assertEquals("a", localVarDeclStat.getNamelist().getFirst().getIdentifier());
    IntegerExp integerExp = (IntegerExp) localVarDeclStat.getOptionalExpList().get().getFirst();
    assertEquals(1, integerExp.getValue());
  }
}
