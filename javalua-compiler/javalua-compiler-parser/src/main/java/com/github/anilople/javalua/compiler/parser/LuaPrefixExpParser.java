package com.github.anilople.javalua.compiler.parser;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_IDENTIFIER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_COLON;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_DOT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_LBRACK;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_LPAREN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_RBRACK;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_RPAREN;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseExp;
import static com.github.anilople.javalua.compiler.parser.LuaParser.canParseArgs;
import static com.github.anilople.javalua.compiler.parser.LuaParser.canParseName;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseArgs;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseName;
import static com.github.anilople.javalua.compiler.parser.ToLuaAstLocationConverter.convert;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.Var.NameVar;
import com.github.anilople.javalua.compiler.ast.Var.TableAccessByExpVar;
import com.github.anilople.javalua.compiler.ast.Var.TableAccessByNameVar;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.FunctionCallPrefixExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.ParenthesesPrefixExp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp.VarPrefixExp;
import com.github.anilople.javalua.compiler.ast.stat.NameFunctionCall;
import com.github.anilople.javalua.compiler.ast.stat.NoNameFunctionCall;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;

/**
 * @author wxq
 */
public class LuaPrefixExpParser {

  /**
   * 前缀表达式只能以标识符或者左圆括号开始
   */
  static boolean canParsePrefixExp(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_LPAREN)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_IDENTIFIER)) {
      return true;
    }
    return false;
  }

  /**
   * page 311
   * <p>
   *
   * <p>
   * <pre>
   *  prefixexp ::= var | functioncall | ‘(’ exp ‘)’
   *
   *  var ::=  Name | prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
   *
   *  functioncall ::=  prefixexp args | prefixexp ‘:’ Name args
   *
   *  args ::=  ‘(’ [explist] ‘)’ | tableconstructor | LiteralString
   *
   *  tableconstructor ::= ‘{’ [fieldlist] ‘}’
   *
   *  prefixexp ::= Name
   * 	| ‘(’ exp ‘)’
   * 	| prefixexp ‘[’ exp ‘]’
   * 	| prefixexp ‘.’ Name
   * 	| prefixexp [‘:’ Name] args
   * </pre>
   */
  static PrefixExp parsePrefixExp(LuaLexer lexer) {
    final PrefixExp prefixExp;
    if (canParseName(lexer)) {
      Name name = parseName(lexer);
      prefixExp = new VarPrefixExp(new NameVar(name));
    } else {
      prefixExp = parseParenthesesPrefixExp(lexer);
    }
    // parse remaining
    return finishParsePrefixExp(lexer, prefixExp);
  }

  /**
   *  prefixexp ::= Name
   * 	| prefixexp ‘[’ exp ‘]’
   * 	| prefixexp ‘.’ Name
   * 	| prefixexp [‘:’ Name] args
   */
  static PrefixExp finishParsePrefixExp(LuaLexer lexer, PrefixExp prefixExp) {
    final PrefixExp nextPrefixExp;
    if (canParseName(lexer)) {
      Name name = parseName(lexer);
      nextPrefixExp = new VarPrefixExp(new NameVar(name));
      return finishParsePrefixExp(lexer, nextPrefixExp);
    } else {
      // var
      if (lexer.lookAheadTest(TOKEN_SEP_LBRACK)) {
        // prefixexp ‘[’ exp ‘]’
        lexer.skip(TOKEN_SEP_LBRACK);
        Exp keyExp = parseExp(lexer);
        lexer.skip(TOKEN_SEP_RBRACK);
        nextPrefixExp = new VarPrefixExp(new TableAccessByExpVar(prefixExp, keyExp));
        return finishParsePrefixExp(lexer, nextPrefixExp);
      }
      if (lexer.lookAheadTest(TOKEN_SEP_DOT)) {
        // prefixexp ‘.’ Name
        lexer.skip(TOKEN_SEP_DOT);
        Name name = parseName(lexer);
        nextPrefixExp = new VarPrefixExp(new TableAccessByNameVar(prefixExp, name));
        return finishParsePrefixExp(lexer, nextPrefixExp);
      }

      // function call
      if (lexer.lookAheadTest(TOKEN_SEP_COLON)) {
        // prefixexp ‘:’ Name args
        lexer.skip(TOKEN_SEP_COLON);
        Name name = parseName(lexer);
        Args args = parseArgs(lexer);
        NameFunctionCall nameFunctionCall = new NameFunctionCall(prefixExp, name, args);
        nextPrefixExp = new FunctionCallPrefixExp(nameFunctionCall);
        return finishParsePrefixExp(lexer, nextPrefixExp);
      }
      if (canParseArgs(lexer)) {
        // prefixexp args
        Args args = parseArgs(lexer);
        NoNameFunctionCall nameFunctionCall = new NoNameFunctionCall(prefixExp, args);
        nextPrefixExp = new FunctionCallPrefixExp(nameFunctionCall);
        return finishParsePrefixExp(lexer, nextPrefixExp);
      }
    }
    return prefixExp;
  }

  static ParenthesesPrefixExp parseParenthesesPrefixExp(LuaLexer lexer) {
    // ‘(’ exp ‘)’
    LuaToken token = lexer.skip(TOKEN_SEP_LPAREN);
    LuaAstLocation location = convert(token);
    Exp exp = parseExp(lexer);
    lexer.skip(TOKEN_SEP_RPAREN);
    return new ParenthesesPrefixExp(location, exp);
  }
}
