package com.github.anilople.javalua.compiler.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaTokenIteratorTest {

  @Test
  void skipComment() {
    LuaResource luaResource = new LuaResource("-- abc");
    LuaTokenIterator luaTokenIterator = new LuaTokenIterator(luaResource);
    luaTokenIterator.skipComment();
    System.out.println(luaResource);
    assertTrue(luaResource.getCurrentLineColumnOffset() > 2);
  }
}
