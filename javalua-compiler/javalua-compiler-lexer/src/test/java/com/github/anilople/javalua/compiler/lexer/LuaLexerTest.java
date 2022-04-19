package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_IDENTIFIER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_END;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_FUNCTION;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_LOCAL;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_RETURN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_NUMBER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_ADD;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_ASSIGN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_DOT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_LPAREN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_RPAREN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_STRING;
import static org.junit.jupiter.api.Assertions.*;

import constant.ResourceContentConstants.ch02;
import constant.ResourceContentConstants.ch14;
import io.LuaTestResource;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.LuaTestResourceUtils;

/**
 * @author wxq
 */
class LuaLexerTest {

  @Test
  void localVariableNumber() {
    List<LuaToken> luaTokens = LuaLexer.lexer("local a = 1");
    assertEquals(4, luaTokens.size());
    {
      LuaToken luaToken = luaTokens.get(0);
      assertEquals(TOKEN_KW_LOCAL, luaToken.getKind());
    }
    {
      LuaToken luaToken = luaTokens.get(1);
      assertEquals(TOKEN_IDENTIFIER, luaToken.getKind());
      assertEquals("a", luaToken.getContent());
    }
    {
      LuaToken luaToken = luaTokens.get(2);
      assertEquals(TOKEN_OP_ASSIGN, luaToken.getKind());
    }
    {
      LuaToken luaToken = luaTokens.get(3);
      assertEquals(TOKEN_NUMBER, luaToken.getKind());
      assertEquals("1", luaToken.getContent());
    }
  }

  @Test
  void ShortString_case1() {
    List<LuaToken> luaTokens = LuaLexer.lexer("'abcd'");
    assertEquals(1, luaTokens.size());
    LuaToken luaToken = luaTokens.get(0);
    assertEquals(TOKEN_STRING, luaToken.getKind());
    assertEquals("abcd", luaToken.getContent());
  }

  @Test
  void ShortString_case2() {
    List<LuaToken> luaTokens = LuaLexer.lexer("\"abcd\"");
    assertEquals(1, luaTokens.size());
    LuaToken luaToken = luaTokens.get(0);
    assertEquals(TOKEN_STRING, luaToken.getKind());
    assertEquals("abcd", luaToken.getContent());
  }

  @Test
  void testLookAhead() {
    LuaLexer luaLexer = LuaLexer.newLuaLexer("function f(a, b) return a + b end");
    {
      LuaToken luaToken = luaLexer.previewNext();
      assertEquals(TOKEN_KW_FUNCTION, luaToken.getKind());
    }
    {
      LuaToken luaToken = luaLexer.lookAhead();
      assertEquals(TOKEN_KW_FUNCTION, luaToken.getKind());
    }
    luaLexer.next();
    {
      LuaToken luaToken = luaLexer.lookAhead();
      assertEquals(TOKEN_IDENTIFIER, luaToken.getKind());
      assertEquals("f", luaToken.getContent());
    }
  }

  @Test
  void testHelloWorld() {
    List<LuaToken> luaTokens = LuaLexer.lexer(ch02.hello_world.getLuaCode());
    luaTokens.forEach(System.out::println);
    assertEquals(4, luaTokens.size());
    {
      LuaToken luaToken = luaTokens.get(0);
      assertEquals(TOKEN_IDENTIFIER, luaToken.getKind());
      assertEquals("print", luaToken.getContent());
    }
    {
      LuaToken luaToken = luaTokens.get(1);
      assertEquals(TOKEN_SEP_LPAREN, luaToken.getKind());
    }
    {
      LuaToken luaToken = luaTokens.get(2);
      assertEquals(TOKEN_STRING, luaToken.getKind());
      assertEquals("Hello, World!", luaToken.getContent());
    }
    {
      LuaToken luaToken = luaTokens.get(3);
      assertEquals(TOKEN_SEP_RPAREN, luaToken.getKind());
    }
  }

  @Test
  void max_case1() {
    List<LuaToken> luaTokens = LuaLexer.lexer(ch14.max_case1.getLuaCode());
    luaTokens.forEach(System.out::println);
  }

  @Test
  void lexerAllLuaCode() throws IOException {
    List<LuaTestResource> luaTestResources = LuaTestResourceUtils.getLuaTestResources();
    System.out.println("luaTestResources size " + luaTestResources.size());
    for (LuaTestResource luaTestResource : luaTestResources) {
      LuaLexer.lexer(luaTestResource.getLuaCode(), luaTestResource.getLuaFilePath());
    }
  }

  @Test
  void function() {
    List<LuaToken> luaTokens = LuaLexer.lexer("function");
    assertEquals(1, luaTokens.size());
    assertEquals(TOKEN_KW_FUNCTION, luaTokens.get(0).getKind());
  }

  @Test
  void identifier_prefix_match_keyword_in() {
    List<LuaToken> luaTokens = LuaLexer.lexer("incr");
    assertEquals(1, luaTokens.size());
    assertEquals(TOKEN_IDENTIFIER, luaTokens.get(0).getKind());
    assertEquals("incr", luaTokens.get(0).getContent());
  }

  @Test
  void identifier_2_keywords() {
    List<LuaToken> luaTokens = LuaLexer.lexer("localfunction");
    assertEquals(1, luaTokens.size());
    assertEquals(TOKEN_IDENTIFIER, luaTokens.get(0).getKind());
    assertEquals("localfunction", luaTokens.get(0).getContent());
  }

  @Test
  void local_function_keyword() {
    List<LuaToken> luaTokens = LuaLexer.lexer("local function");
    assertEquals(2, luaTokens.size());
    assertEquals(TOKEN_KW_LOCAL, luaTokens.get(0).getKind());
    assertEquals(TOKEN_KW_FUNCTION, luaTokens.get(1).getKind());
  }

  @Test
  void function_incr() {
    List<LuaToken> luaTokens = LuaLexer.lexer("function incr(value) return value + 1 end");
    assertEquals(TOKEN_KW_FUNCTION, luaTokens.get(0).getKind());

    // incr(value)
    assertEquals(TOKEN_IDENTIFIER, luaTokens.get(1).getKind());
    assertEquals(TOKEN_SEP_LPAREN, luaTokens.get(2).getKind());
    assertEquals(TOKEN_IDENTIFIER, luaTokens.get(3).getKind());
    assertEquals(TOKEN_SEP_RPAREN, luaTokens.get(4).getKind());

    // return value + 1 end
    assertEquals(TOKEN_KW_RETURN, luaTokens.get(5).getKind());
    assertEquals(TOKEN_IDENTIFIER, luaTokens.get(6).getKind());
    assertEquals(TOKEN_OP_ADD, luaTokens.get(7).getKind());
    assertEquals(TOKEN_NUMBER, luaTokens.get(8).getKind());
    assertEquals(TOKEN_KW_END, luaTokens.get(9).getKind());
  }

  @Test
  void function_call_with_module() {
    List<LuaToken> luaTokens = LuaLexer.lexer("mymodule.f()");
    assertEquals(5, luaTokens.size());
    assertEquals("mymodule", luaTokens.get(0).getContent());
    assertEquals(TOKEN_SEP_DOT, luaTokens.get(1).getKind());
    assertEquals("f", luaTokens.get(2).getContent());
    assertEquals(TOKEN_SEP_LPAREN, luaTokens.get(3).getKind());
    assertEquals(TOKEN_SEP_RPAREN, luaTokens.get(4).getKind());
  }
}
