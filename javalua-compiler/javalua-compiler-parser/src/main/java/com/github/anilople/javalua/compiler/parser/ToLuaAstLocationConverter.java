package com.github.anilople.javalua.compiler.parser;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import com.github.anilople.javalua.compiler.lexer.LuaTokenLocation;

/**
 * @author wxq
 */
class ToLuaAstLocationConverter {

  static LuaAstLocation convert(LuaTokenLocation luaTokenLocation) {
    return new LuaAstLocation(luaTokenLocation.getLineNumber(), luaTokenLocation.getColumnNumber());
  }

  static LuaAstLocation convert(LuaToken luaToken) {
    return convert(luaToken.getLocation());
  }
}
