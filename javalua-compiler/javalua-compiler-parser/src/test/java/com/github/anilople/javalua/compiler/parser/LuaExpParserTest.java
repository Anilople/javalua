package com.github.anilople.javalua.compiler.parser;

import com.github.anilople.javalua.compiler.ast.Field;
import com.github.anilople.javalua.compiler.ast.Field.NameField;
import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.exp.IntegerExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxq
 */
class LuaExpParserTest {

  @Test
  void parseTableConstructorExp_empty() {
    LuaLexer lexer = LuaLexer.newLuaLexer("{}");
    TableConstructorExp tableConstructorExp = LuaExpParser.parseTableConstructorExp(lexer);
    assertTrue(tableConstructorExp.getOptionalFieldList().isEmpty());
  }


  @Test
  void parseTableConstructorExp_1_elments() {
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
  void parseTableConstructorExp_2_elments() {
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
}