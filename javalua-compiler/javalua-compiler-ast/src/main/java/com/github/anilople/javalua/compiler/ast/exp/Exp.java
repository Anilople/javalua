package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAst;

/**
 * 	exp ::=  nil | false | true | Numeral | LiteralString | ‘...’ | functiondef |
 * 		 prefixexp | tableconstructor | exp binop exp | unop exp
 *
 * @author wxq
 */
public interface Exp extends LuaAst {}
