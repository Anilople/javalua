package com.github.anilople.javalua.compiler.parser;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_FALSE;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_FUNCTION;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_NIL;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_KW_TRUE;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_NUMBER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_ADD;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_AND;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_BAND;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_BNOT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_BOR;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_CONCAT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_DIV;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_EQ;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_GE;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_GT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_IDIV;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_LE;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_LEN;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_LT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_MOD;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_MUL;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_NE;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_NOT;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_OR;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_POW;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_SHL;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_SHR;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_OP_SUB;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_COMMA;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_LCURLY;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_SEP_RCURLY;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_STRING;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_VARARG;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseBinop;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseFuncBody;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseOptionalFieldList;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseUnop;
import static com.github.anilople.javalua.compiler.parser.LuaPrefixExpParser.canParsePrefixExp;
import static com.github.anilople.javalua.compiler.parser.LuaPrefixExpParser.parsePrefixExp;
import static com.github.anilople.javalua.compiler.parser.ToLuaAstLocationConverter.convert;

import com.github.anilople.javalua.compiler.ast.Binop;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Unop;
import com.github.anilople.javalua.compiler.ast.exp.BinopExp;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.FalseExp;
import com.github.anilople.javalua.compiler.ast.exp.FloatExp;
import com.github.anilople.javalua.compiler.ast.exp.FunctionDefExp;
import com.github.anilople.javalua.compiler.ast.exp.IntegerExp;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.NilExp;
import com.github.anilople.javalua.compiler.ast.exp.NumeralExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import com.github.anilople.javalua.compiler.ast.exp.TrueExp;
import com.github.anilople.javalua.compiler.ast.exp.UnopExp;
import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import com.github.anilople.javalua.util.LuaConvertUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * page 303
 * <p>
 * 根据运算符优先级把表达式的语法规则重写，得到
 *
 * <pre>
 *  exp ::=  nil | false | true | Numeral | LiteralString | ‘...’ | functiondef |
 *  	 prefixexp | tableconstructor | exp binop exp | unop exp
 *
 *  exp   ::= exp12
 *  exp12 ::= exp11 {or exp11}
 *  exp11 ::= exp10 {and exp10}
 *  exp10 ::= exp9 {(‘<’ | ‘>’ | ‘<=’ | ‘>=’ | ‘~=’ | ‘==’) exp9}
 *  exp9  ::= exp8 {‘|’ exp8}
 *  exp8  ::= exp7 {‘~’ exp7}
 *  exp7  ::= exp6 {‘&’ exp6}
 *  exp6  ::= exp5 {(‘<<’ | ‘>>’) exp5}
 *  exp5  ::= exp4 {‘..’ exp4}
 *  exp4  ::= exp3 {(‘+’ | ‘-’) exp3}
 *  exp3  ::= exp2 {(‘*’ | ‘/’ | ‘//’ | ‘%’) exp2}
 *  exp2  ::= {(‘not’ | ‘#’ | ‘-’ | ‘~’)} exp1
 *  exp1  ::= exp0 {‘^’ exp2}
 *  exp0  ::= nil | false | true | Numeral | LiteralString
 *  		| ‘...’ | functiondef | prefixexp | tableconstructor
 * </pre>
 *
 * @author wxq
 */
class LuaExpParser {

  /**
   * page 305
   *
   * <p>
   *
   * expX {op expY} 模式的parse
   * <p>
   * 假设expX已经获取
   */
  static Exp resolveBinopExp(
      LuaLexer lexer,
      Exp expX,
      Predicate<TokenEnums> opPredicate,
      Function<LuaLexer, Exp> functionOfParseExpY) {
    if (!lexer.hasNext()) {
      return expX;
    }
    LuaToken token = lexer.lookAhead();
    TokenEnums opKind = token.getKind();
    if (opPredicate.test(opKind)) {
      // 放到左子树
      Binop binop = parseBinop(lexer);
      Exp expY = functionOfParseExpY.apply(lexer);
      BinopExp binopExp = new BinopExp(expY, binop, expY);
      return resolveBinopExp(lexer, binopExp, opPredicate, functionOfParseExpY);
    } else {
      return expX;
    }
  }

  static boolean canParseExp(LuaLexer lexer) {
    return canParseExp2(lexer);
  }

  /**
   * Exp ::= exp12
   */
  static Exp parseExp(LuaLexer lexer) {
    return parseExp12(lexer);
  }

  /**
   * Exp12 ::= exp11 {or exp11}
   */
  static Exp parseExp12(LuaLexer lexer) {
    Exp exp11 = parseExp11(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_OR)) {
      return resolveBinopExp(lexer, exp11, TOKEN_OP_OR::equals, LuaExpParser::parseExp11);
    } else {
      return exp11;
    }
  }

  /**
   * Exp11 ::= exp10 {and exp10}
   */
  static Exp parseExp11(LuaLexer lexer) {
    Exp exp10 = parseExp10(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_AND)) {
      return resolveBinopExp(lexer, exp10, TOKEN_OP_AND::equals, LuaExpParser::parseExp10);
    } else {
      return exp10;
    }
  }

  /**
   * Exp10 ::= exp9 {(‘<’ | ‘>’ | ‘<=’ | ‘>=’ | ‘~=’ | ‘==’) exp9}
   */
  static Exp parseExp10(LuaLexer lexer) {
    Exp exp9 = parseExp9(lexer);
    Predicate<TokenEnums> predicate =
        tokenEnums -> {
          if (TOKEN_OP_LT.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_GT.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_LE.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_GE.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_NE.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_EQ.equals(tokenEnums)) {
            return true;
          }
          return false;
        };
    LuaToken token = lexer.lookAhead();
    if (predicate.test(token.getKind())) {
      return resolveBinopExp(lexer, exp9, predicate, LuaExpParser::parseExp9);
    } else {
      return exp9;
    }
  }

  /**
   * Exp9 ::= exp8 {‘|’ exp8}
   */
  static Exp parseExp9(LuaLexer lexer) {
    Exp exp8 = parseExp8(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_BOR)) {
      return resolveBinopExp(lexer, exp8, TOKEN_OP_BOR::equals, LuaExpParser::parseExp8);
    } else {
      return exp8;
    }
  }

  /**
   * Exp8 ::= exp7 {‘~’ exp7}
   */
  static Exp parseExp8(LuaLexer lexer) {
    Exp exp7 = parseExp7(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_BNOT)) {
      return resolveBinopExp(lexer, exp7, TOKEN_OP_BNOT::equals, LuaExpParser::parseExp7);
    } else {
      return exp7;
    }
  }

  /**
   * Exp7 ::= exp6 {‘&’ exp6}
   */
  static Exp parseExp7(LuaLexer lexer) {
    Exp exp6 = parseExp6(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_BAND)) {
      return resolveBinopExp(lexer, exp6, TOKEN_OP_BAND::equals, LuaExpParser::parseExp6);
    } else {
      return exp6;
    }
  }

  /**
   * Exp6 ::= exp5 {(‘<<’ | ‘>>’) exp5}
   */
  static Exp parseExp6(LuaLexer lexer) {
    Exp exp5 = parseExp5(lexer);
    Predicate<TokenEnums> predicate =
        tokenEnums -> {
          if (TOKEN_OP_SHL.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_SHR.equals(tokenEnums)) {
            return true;
          }
          return false;
        };
    LuaToken token = lexer.lookAhead();
    if (predicate.test(token.getKind())) {
      return resolveBinopExp(lexer, exp5, predicate, LuaExpParser::parseExp5);
    } else {
      return exp5;
    }
  }

  /**
   * Exp5 ::= exp4 {‘..’ exp4}
   */
  static Exp parseExp5(LuaLexer lexer) {
    Exp exp4 = parseExp4(lexer);
    if (lexer.lookAheadTest(TOKEN_OP_CONCAT)) {
      return resolveBinopExp(lexer, exp4, TOKEN_OP_CONCAT::equals, LuaExpParser::parseExp4);
    } else {
      return exp4;
    }
  }

  /**
   * Exp4 ::= exp3 {(‘+’ | ‘-’) exp3}
   */
  static Exp parseExp4(LuaLexer lexer) {
    Exp exp3 = parseExp3(lexer);
    Predicate<TokenEnums> predicate =
        tokenEnums -> {
          if (TOKEN_OP_ADD.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_SUB.equals(tokenEnums)) {
            return true;
          }
          return false;
        };
    LuaToken token = lexer.lookAhead();
    if (predicate.test(token.getKind())) {
      return resolveBinopExp(lexer, exp3, predicate, LuaExpParser::parseExp3);
    } else {
      return exp3;
    }
  }

  /**
   * Exp3 ::= exp2 {(‘*’ | ‘/’ | ‘//’ | ‘%’) exp2}
   */
  static Exp parseExp3(LuaLexer lexer) {
    Exp exp2 = parseExp2(lexer);
    Predicate<TokenEnums> predicate =
        tokenEnums -> {
          if (TOKEN_OP_MUL.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_DIV.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_IDIV.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_MOD.equals(tokenEnums)) {
            return true;
          }
          return false;
        };
    LuaToken token = lexer.lookAhead();
    if (predicate.test(token.getKind())) {
      return resolveBinopExp(lexer, exp2, predicate, LuaExpParser::parseExp2);
    } else {
      return exp2;
    }
  }

  /**
   * Exp2 ::= {(‘not’ | ‘#’ | ‘-’ | ‘~’)} exp1
   */
  static boolean canParseExp2(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_OP_NOT)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_OP_LEN)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_OP_SUB)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_OP_BNOT)) {
      return true;
    }
    return canParseExp1(lexer);
  }

  /**
   * Exp2 ::= {(‘not’ | ‘#’ | ‘-’ | ‘~’)} exp1
   *
   * @see UnopExp
   */
  static Exp parseExp2(LuaLexer lexer) {
    Predicate<TokenEnums> predicate =
        tokenEnums -> {
          if (TOKEN_OP_NOT.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_LEN.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_SUB.equals(tokenEnums)) {
            return true;
          }
          if (TOKEN_OP_BNOT.equals(tokenEnums)) {
            return true;
          }
          return false;
        };
    LuaToken token = lexer.lookAhead();
    if (predicate.test(token.getKind())) {
      Unop unop = parseUnop(lexer);
      Exp exp1 = parseExp1(lexer);
      return new UnopExp(unop, exp1);
    } else {
      return parseExp1(lexer);
    }
  }

  /**
   * Exp1 ::= exp0 {‘^’ exp2}
   */
  static boolean canParseExp1(LuaLexer lexer) {
    return canParseExp0(lexer);
  }

  /**
   * Exp1 ::= exp0 {‘^’ exp2}
   */
  static Exp parseExp1(LuaLexer lexer) {
    Exp exp0 = parseExp0(lexer);
    return resolveBinopExp(lexer, exp0, TOKEN_OP_POW::equals, LuaExpParser::parseExp2);
  }

  /**
   * Exp0 ::= nil | false | true | Numeral | LiteralString | ‘...’ | functiondef | prefixexp |
   * tableconstructor
   */
  static boolean canParseExp0(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_KW_NIL)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_KW_FALSE)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_KW_TRUE)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_STRING)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_NUMBER)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_VARARG)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_KW_FUNCTION)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_SEP_LCURLY)) {
      // tableconstructor ::= ‘{’ [fieldlist] ‘}’
      return true;
    }
    return canParsePrefixExp(lexer);
  }

  /**
   * Exp0 ::= nil | false | true | Numeral | LiteralString | ‘...’ | functiondef | prefixexp |
   * tableconstructor
   */
  static Exp parseExp0(LuaLexer lexer) {
    LuaToken token = lexer.lookAhead();
    LuaAstLocation location = convert(token);
    switch (token.getKind()) {
      case TOKEN_KW_NIL:
        return new NilExp(location);
      case TOKEN_KW_FALSE:
        return new FalseExp(location);
      case TOKEN_KW_TRUE:
        return new TrueExp(location);
      case TOKEN_STRING:
        return parseLiteralStringExp(lexer);
      case TOKEN_NUMBER:
        return parseNumeralExp(lexer);
      case TOKEN_VARARG:
        return new VarargExp(location);
      case TOKEN_KW_FUNCTION:
        return parseFunctionDefExp(lexer);
      case TOKEN_SEP_LCURLY:
        // tableconstructor ::= ‘{’ [fieldlist] ‘}’
        return parseTableConstructorExp(lexer);
      default:
        return parsePrefixExp(lexer);
    }
  }

  /**
   * explist ::= exp {‘,’ exp}
   */
  static boolean canParseExpList(LuaLexer lexer) {
    return canParseExp(lexer);
  }

  /**
   * explist ::= exp {‘,’ exp}
   */
  static ExpList parseExpList(LuaLexer lexer) {
    Exp first = parseExp(lexer);
    List<Exp> tail = new ArrayList<>();
    while (lexer.lookAheadTest(TOKEN_SEP_COMMA)) {
      lexer.skip(TOKEN_SEP_COMMA);
      Exp exp = parseExp(lexer);
      tail.add(exp);
    }
    return new ExpList(first, tail);
  }

  static Optional<ExpList> parseOptionalExpList(LuaLexer lexer) {
    if (canParseExpList(lexer)) {
      ExpList expList = parseExpList(lexer);
      return Optional.of(expList);
    } else {
      return Optional.empty();
    }
  }

  /**
   * tableconstructor ::= ‘{’ [fieldlist] ‘}’
   */
  static TableConstructorExp parseTableConstructorExp(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TOKEN_SEP_LCURLY);
      location = convert(token);
    }
    Optional<FieldList> optionalFieldList = parseOptionalFieldList(lexer);
    lexer.skip(TOKEN_SEP_RCURLY);
    return new TableConstructorExp(location, optionalFieldList);
  }

  static LiteralStringExp parseLiteralStringExp(LuaLexer lexer) {
    LuaToken token = lexer.skip(TOKEN_STRING);
    LuaAstLocation location = convert(token);
    return new LiteralStringExp(location, token.getContent());
  }

  static NumeralExp parseNumeralExp(LuaLexer lexer) {
    LuaToken token = lexer.skip(TOKEN_NUMBER);
    LuaAstLocation location = convert(token);
    String content = token.getContent();
    var rOfInteger = LuaConvertUtils.convertToLuaInteger(content);
    if (rOfInteger.r0) {
      return new IntegerExp(location, rOfInteger.r1);
    }
    var rOfFloat = LuaConvertUtils.convertToLuaNumber(content);
    if (rOfFloat.r0) {
      return new FloatExp(location, rOfFloat.r1);
    }
    throw new IllegalStateException("cannot convert '" + content + "' to lua number");
  }

  /**
   * functiondef ::= function funcbody
   */
  static FunctionDefExp parseFunctionDefExp(LuaLexer lexer) {
    LuaToken functionToken = lexer.next();
    LuaAstLocation location = convert(functionToken);
    FuncBody funcBody = parseFuncBody(lexer);
    return new FunctionDefExp(location, funcBody);
  }

  static VarargExp parseVarargExp(LuaLexer lexer) {
    LuaToken token = lexer.skip(TOKEN_VARARG);
    LuaAstLocation location = convert(token);
    return new VarargExp(location);
  }
}
