package com.github.anilople.javalua.compiler.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.compiler.ast.Field.NameField;
import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.ParList.NameListParList;
import com.github.anilople.javalua.compiler.ast.Unop.MinusUnop;
import com.github.anilople.javalua.compiler.ast.exp.BinopExp;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.FalseExp;
import com.github.anilople.javalua.compiler.ast.exp.FloatExp;
import com.github.anilople.javalua.compiler.ast.exp.FunctionDefExp;
import com.github.anilople.javalua.compiler.ast.exp.IntegerExp;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.NilExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.ParenthesesPrefixExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import com.github.anilople.javalua.compiler.ast.exp.TrueExp;
import com.github.anilople.javalua.compiler.ast.exp.UnopExp;
import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import org.junit.jupiter.api.Test;

/**
 * 	exp ::=  nil | false | true | Numeral | LiteralString | ‘...’ | functiondef |
 * 		 prefixexp | tableconstructor | exp binop exp | unop exp
 * @author wxq
 */
class LuaExpParserTest {

  static Exp parseExp(String luaCode) {
    LuaLexer lexer = LuaLexer.newLuaLexer(luaCode);
    return LuaExpParser.parseExp(lexer);
  }

  /**
   * nil
   */
  @Test
  void test_nil() {
    Exp exp = parseExp("nil");
    assertTrue(exp instanceof NilExp);
  }

  /**
   * false
   */
  @Test
  void test_false() {
    Exp exp = parseExp("false");
    assertTrue(exp instanceof FalseExp);
  }

  /**
   * true
   */
  @Test
  void test_true() {
    Exp exp = parseExp("true");
    assertTrue(exp instanceof TrueExp);
  }

  /**
   * Numeral
   */
  @Test
  void test_Numeral_integer() {
    Exp exp = parseExp("123");
    assertTrue(exp instanceof IntegerExp);
    IntegerExp integerExp = (IntegerExp) exp;
    assertEquals(123, integerExp.getValue());
  }

  /**
   * Numeral
   */
  @Test
  void test_Numeral_number() {
    Exp exp = parseExp("123.456");
    assertTrue(exp instanceof FloatExp);
    FloatExp floatExp = (FloatExp) exp;
    assertEquals(123.456, floatExp.getValue());
  }

  /**
   * LiteralString
   */
  @Test
  void test_LiteralString() {
    Exp exp = parseExp("'abcd'");
    assertTrue(exp instanceof LiteralStringExp);
    LiteralStringExp literalStringExp = (LiteralStringExp) exp;
    assertEquals("abcd", literalStringExp.getContent());
  }

  /**
   * ‘...’
   */
  @Test
  void test_vararg() {
    Exp exp = parseExp("...");
    assertTrue(exp instanceof VarargExp);
  }

  @Test
  void test_functiondef_empty() {
    Exp exp = parseExp("function () end");
    assertTrue(exp instanceof FunctionDefExp);
    FunctionDefExp functionDefExp = (FunctionDefExp) exp;
    FuncBody funcBody = functionDefExp.getFuncBody();
    assertTrue(funcBody.getOptionalParList().isEmpty());
  }

  @Test
  void test_functiondef_add() {
    Exp exp = parseExp("function (a, b) return a + b end");
    assertTrue(exp instanceof FunctionDefExp);
    FunctionDefExp functionDefExp = (FunctionDefExp) exp;
    FuncBody funcBody = functionDefExp.getFuncBody();
    NameListParList nameListParList = (NameListParList) funcBody.getOptionalParList().get();
    NameList nameList = nameListParList.getNameList();
    assertEquals(2, nameList.size());
    assertEquals("a", nameList.get(0).getIdentifier());
    assertEquals("b", nameList.get(1).getIdentifier());
  }

  /**
   * prefixexp
   */
  @Test
  void test_prefixexp() {
    Exp exp = parseExp("(...)");
    assertTrue(exp instanceof ParenthesesPrefixExp);
    ParenthesesPrefixExp parenthesesPrefixExp = (ParenthesesPrefixExp) exp;
    assertTrue(parenthesesPrefixExp.getExp() instanceof VarargExp);
  }

  @Test
  void test_tableconstructor_empty() {
    LuaLexer lexer = LuaLexer.newLuaLexer("{}");
    TableConstructorExp tableConstructorExp = LuaExpParser.parseTableConstructorExp(lexer);
    assertTrue(tableConstructorExp.getOptionalFieldList().isEmpty());
  }

  @Test
  void test_tableconstructor_1_elments() {
    LuaLexer lexer = LuaLexer.newLuaLexer("{x=999}");
    TableConstructorExp tableConstructorExp = LuaExpParser.parseTableConstructorExp(lexer);
    FieldList fieldList = tableConstructorExp.getOptionalFieldList().get();
    assertEquals(1, fieldList.size());

    {
      NameField field = (NameField) fieldList.get(0);
      assertEquals("x", field.getName().getIdentifier());
      IntegerExp integerExp = (IntegerExp) field.getExp();
      assertEquals(999, integerExp.getValue());
    }
  }

  @Test
  void test_tableconstructor_2_elments() {
    LuaLexer lexer = LuaLexer.newLuaLexer("{x=1, y=2}");
    TableConstructorExp tableConstructorExp = LuaExpParser.parseTableConstructorExp(lexer);
    FieldList fieldList = tableConstructorExp.getOptionalFieldList().get();
    assertEquals(2, fieldList.size());

    {
      NameField field = (NameField) fieldList.get(0);
      assertEquals("x", field.getName().getIdentifier());
      IntegerExp integerExp = (IntegerExp) field.getExp();
      assertEquals(1, integerExp.getValue());
    }

    {
      NameField field = (NameField) fieldList.get(1);
      assertEquals("y", field.getName().getIdentifier());
      IntegerExp integerExp = (IntegerExp) field.getExp();
      assertEquals(2, integerExp.getValue());
    }
  }

  /**
   * exp binop exp
   */
  @Test
  void test_binop_plus() {
    BinopExp binopExp = (BinopExp) parseExp("111 + 222");
    assertEquals(111, ((IntegerExp) binopExp.getExp1()).getValue());
    assertEquals(222, ((IntegerExp) binopExp.getExp2()).getValue());
  }

  /**
   * unop exp
   */
  @Test
  void test_unop_minus() {
    UnopExp unopExp = (UnopExp) parseExp("- 3");
    assertTrue(unopExp.getUnop() instanceof MinusUnop);
    IntegerExp integerExp = (IntegerExp) unopExp.getExp();
    assertEquals(3, integerExp.getValue());
  }
}
