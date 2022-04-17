package com.github.anilople.javalua.compiler.parser;

import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseExpList;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parsePrefixExp;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseArgs;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseBlock;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseFuncBody;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseFuncname;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseName;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseNameList;
import static com.github.anilople.javalua.compiler.parser.LuaParser.parseVarList;
import static com.github.anilople.javalua.compiler.parser.ToLuaAstLocationConverter.convert;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.FuncName;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.VarList;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import com.github.anilople.javalua.compiler.ast.stat.*;
import com.github.anilople.javalua.compiler.ast.stat.EmptyStat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * @author wxq
 */
class LuaStatParser {

  static List<Stat> parseStatList(LuaLexer lexer) {
    return null;
  }

  /**
   * <pre>
   * 	stat ::=  ‘;’ |
   * 		 varlist ‘=’ explist |
   * 		 functioncall |
   * 		 label |
   * 		 break |
   * 		 goto Name |
   * 		 do block end |
   * 		 while exp do block end |
   * 		 repeat block until exp |
   * 		 if exp then block {elseif exp then block} [else block] end |
   * 		 for Name ‘=’ exp ‘,’ exp [‘,’ exp] do block end |
   * 		 for namelist in explist do block end |
   * 		 function funcname funcbody |
   * 		 local function Name funcbody |
   * 		 local namelist [‘=’ explist]
   * </pre>
   */
  static Stat parseStat(LuaLexer lexer) {
    LuaToken token = lexer.lookAhead();
    switch (token.getKind()) {
      case TOKEN_SEP_SEMI:
        return parseEmptyStat(lexer);
      case TOKEN_KW_BREAK:
        return parseBreakStat(lexer);
      case TOKEN_SEP_LABEL:
        return parseLabelStat(lexer);
      case TOKEN_KW_GOTO:
        return parseGotoStat(lexer);
      case TOKEN_KW_DO:
        return parseDoStat(lexer);
      case TOKEN_KW_WHILE:
        return parseWhileStat(lexer);
      case TOKEN_KW_REPEAT:
        return parseRepeatStat(lexer);
      case TOKEN_KW_IF:
        return parseIfStat(lexer);
      case TOKEN_KW_FOR:
        return parseForStat(lexer);
      case TOKEN_KW_FUNCTION:
        return parseFunctionDefineStat(lexer);
      case TOKEN_KW_LOCAL:
        return parseLocalAssignOrFuncDefStat(lexer);
      default:
        return parseAssignOrFunctionCallStat(lexer);
    }
  }

  static GotoStat parseGotoStat(LuaLexer lexer) {
    lexer.skip(TokenEnums.TOKEN_KW_GOTO);
    Name name = parseName(lexer);
    return new GotoStat(name);
  }

  /**
   * local function Name funcbody
   */
  static LocalFunctionDefineStat parseLocalFunctionDefineStat(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_KW_LOCAL);
      location = convert(token);
    }
    lexer.skip(TokenEnums.TOKEN_KW_FUNCTION);
    Name name = parseName(lexer);
    FuncBody funcBody = parseFuncBody(lexer);
    return new LocalFunctionDefineStat(location, name, funcBody);
  }

  static LabelStat parseLabelStat(LuaLexer lexer) {
    lexer.skip(TokenEnums.TOKEN_SEP_LABEL);
    Name name = parseName(lexer);
    lexer.skip(TokenEnums.TOKEN_SEP_LABEL);
    return new LabelStat(name);
  }

  static RepeatStat parseRepeatStat(LuaLexer lexer) {
    LuaToken repeatToken = lexer.skip(TokenEnums.TOKEN_KW_REPEAT);
    Block block = parseBlock(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_UNTIL);
    Exp exp = parseExp(lexer);
    LuaAstLocation luaAstLocation = convert(repeatToken);
    return new RepeatStat(luaAstLocation, block, exp);
  }

  static EmptyStat parseEmptyStat(LuaLexer lexer) {
    LuaToken token = lexer.next();
    LuaAstLocation luaAstLocation = convert(token.getLocation());
    return new EmptyStat(luaAstLocation);
  }

  /**
   * local namelist [‘=’ explist]
   */
  static LocalVarDeclStat parseLocalVarDeclStat(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_KW_LOCAL);
      location = convert(token);
    }
    final NameList nameList = parseNameList(lexer);
    final Optional<ExpList> optionalExpList;
    if (lexer.lookAheadTest(TokenEnums.TOKEN_OP_EQ)) {
      lexer.skip(TokenEnums.TOKEN_OP_EQ);
      ExpList expList = parseExpList(lexer);
      optionalExpList = Optional.of(expList);
    } else {
      optionalExpList = Optional.empty();
    }
    return new LocalVarDeclStat(location, nameList, optionalExpList);
  }

  /**
   * functioncall ::=  prefixexp args | prefixexp ‘:’ Name args
   */
  static FunctionCall parseFunctionCall(LuaLexer lexer) {
    PrefixExp prefixExp = parsePrefixExp(lexer);
    if (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_COLON)) {
      lexer.skip(TokenEnums.TOKEN_SEP_COLON);
      Name name = parseName(lexer);
      Args args = parseArgs(lexer);
      return new NameFunctionCall(prefixExp, name, args);
    } else {
      Args args = parseArgs(lexer);
      return new NoNameFunctionCall(prefixExp, args);
    }
  }

  static BreakStat parseBreakStat(LuaLexer lexer) {
    LuaToken token = lexer.next();
    LuaAstLocation luaAstLocation = convert(token.getLocation());
    return new BreakStat(luaAstLocation);
  }

  static WhileStat parseWhileStat(LuaLexer lexer) {
    LuaToken whileToken = lexer.skip(TokenEnums.TOKEN_KW_WHILE);
    Exp exp = parseExp(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_DO);
    Block block = parseBlock(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_END);
    LuaAstLocation luaAstLocation = convert(whileToken);
    return new WhileStat(luaAstLocation, exp, block);
  }

  static DoStat parseDoStat(LuaLexer lexer) {
    LuaToken doToken = lexer.skip(TokenEnums.TOKEN_KW_DO);
    Block block = parseBlock(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_END);
    LuaAstLocation luaAstLocation = convert(doToken);
    return new DoStat(luaAstLocation, block);
  }

  static FunctionDefineStat parseFunctionDefineStat(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_KW_FUNCTION);
      location = convert(token);
    }
    FuncName funcname = parseFuncname(lexer);
    FuncBody funcBody = parseFuncBody(lexer);
    return new FunctionDefineStat(location, funcname, funcBody);
  }

  /**
   * if exp then block {elseif exp then block} [else block] end
   */
  static IfStat parseIfStat(LuaLexer lexer) {
    LuaToken ifToken = lexer.skip(TokenEnums.TOKEN_KW_IF);
    final Exp exp = parseExp(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_THEN);
    final Block block = parseBlock(lexer);

    // {elseif exp then block}
    final List<Entry<Exp, Block>> elseif = new ArrayList<>();
    while (lexer.lookAheadTest(TokenEnums.TOKEN_KW_ELSEIF)) {
      lexer.skip(TokenEnums.TOKEN_KW_ELSEIF);
      Exp elseifExp = parseExp(lexer);
      lexer.skip(TokenEnums.TOKEN_KW_THEN);
      Block elseifBlock = parseBlock(lexer);
      var entry = new SimpleImmutableEntry<>(elseifExp, elseifBlock);
      elseif.add(entry);
    }

    // [else block]
    final Optional<Block> elseBlock;
    if (lexer.lookAheadTest(TokenEnums.TOKEN_KW_ELSE)) {
      lexer.skip(TokenEnums.TOKEN_KW_ELSE);
      elseBlock = Optional.of(parseBlock(lexer));
    } else {
      elseBlock = Optional.empty();
    }
    lexer.skip(TokenEnums.TOKEN_KW_END);

    LuaAstLocation luaAstLocation = convert(ifToken);
    return new IfStat(luaAstLocation, exp, block, elseif, elseBlock);
  }

  /**
   * for namelist in explist do block end
   */
  static ForInStat parseForInStat(LuaLexer lexer) {
    final LuaToken forToken = lexer.skip(TokenEnums.TOKEN_KW_FOR);
    final LuaAstLocation locationOfFor = convert(forToken);
    NameList nameList = parseNameList(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_IN);
    ExpList expList = parseExpList(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_DO);
    Block block = parseBlock(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_END);
    return new ForInStat(locationOfFor, nameList, expList, block);
  }

  /**
   * for Name ‘=’ exp ‘,’ exp [‘,’ exp] do block end
   */
  static ForNumStat parseForNumStat(LuaLexer lexer) {
    final LuaToken forToken = lexer.skip(TokenEnums.TOKEN_KW_FOR);
    final LuaAstLocation locationOfFor = convert(forToken);

    // Name ‘=’ exp ‘,’
    final Name name = parseName(lexer);
    lexer.skip(TokenEnums.TOKEN_OP_EQ);
    final Exp initExp = parseExp(lexer);
    lexer.skip(TokenEnums.TOKEN_SEP_COMMA);

    final Exp limitExp = parseExp(lexer);

    // [‘,’ exp]
    final Optional<Exp> stepExp;
    if (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_COMMA)) {
      lexer.skip(TokenEnums.TOKEN_SEP_COMMA);
      stepExp = Optional.of(parseExp(lexer));
    } else {
      stepExp = Optional.empty();
    }

    // do block end
    final LuaToken doToken = lexer.skip(TokenEnums.TOKEN_KW_DO);
    final LuaAstLocation locationOfDo = convert(doToken);
    final Block block = parseBlock(lexer);
    lexer.skip(TokenEnums.TOKEN_KW_END);

    return new ForNumStat(locationOfFor, locationOfDo, name, initExp, limitExp, stepExp, block);
  }

  /**
   * varlist ‘=’ explist
   */
  static AssignStat parseAssignStat(LuaLexer lexer) {
    VarList varList = parseVarList(lexer);
    lexer.skip(TokenEnums.TOKEN_OP_EQ);
    ExpList expList = parseExpList(lexer);
    return new AssignStat(varList, expList);
  }

  /**
   * for namelist in explist do block end
   * <p>
   * namelist ::= Name {‘,’ Name}
   * <p>
   * for Name ‘=’ exp ‘,’ exp [‘,’ exp] do block end
   * <p>
   * 所以是看第三个token是不是 '='
   */
  static Stat parseForStat(LuaLexer lexer) {
    List<LuaToken> tokens = lexer.previewNext(3);
    if (TokenEnums.TOKEN_OP_EQ.equals(tokens.get(2).getKind())) {
      return parseForNumStat(lexer);
    } else {
      return parseForInStat(lexer);
    }
  }

  /**
   * local function Name funcbody
   * <p>
   * local namelist [‘=’ explist]
   */
  static Stat parseLocalAssignOrFuncDefStat(LuaLexer lexer) {
    List<LuaToken> tokens = lexer.previewNext(2);
    if (TokenEnums.TOKEN_KW_FUNCTION.equals(tokens.get(1).getKind())) {
      return parseFunctionDefineStat(lexer);
    } else {
      return parseLocalVarDeclStat(lexer);
    }
  }

  /**
   * page 300
   * <p>
   * varlist ‘=’ explist
   * <p>
   * functioncall
   */
  static Stat parseAssignOrFunctionCallStat(LuaLexer lexer) {
    if (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_LPAREN)) {
      // it is a functioncall
      return parseFunctionCall(lexer);
    } else {
      return parseAssignStat(lexer);
    }
  }
}