package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_IDENTIFIER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_FUNCTION;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_LOCAL;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_NUMBER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_ASSIGN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_LPAREN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_RPAREN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_STRING;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

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
    List<LuaToken> luaTokens = LuaLexer.lexer("print(\"Hello, World!\")");
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
}
