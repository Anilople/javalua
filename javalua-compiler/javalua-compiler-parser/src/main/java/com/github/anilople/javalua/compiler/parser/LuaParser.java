package com.github.anilople.javalua.compiler.parser;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.Args.ExpListArgs;
import com.github.anilople.javalua.compiler.ast.Args.LiteralStringArgs;
import com.github.anilople.javalua.compiler.ast.Args.TableConstructorArgs;
import com.github.anilople.javalua.compiler.ast.Binop;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.FuncName;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.ParList;
import com.github.anilople.javalua.compiler.ast.Retstat;
import com.github.anilople.javalua.compiler.ast.Unop;
import com.github.anilople.javalua.compiler.ast.Unop.BitNotUnop;
import com.github.anilople.javalua.compiler.ast.Unop.LengthUnop;
import com.github.anilople.javalua.compiler.ast.Unop.MinusUnop;
import com.github.anilople.javalua.compiler.ast.Unop.NotUnop;
import com.github.anilople.javalua.compiler.ast.Var;
import com.github.anilople.javalua.compiler.ast.VarList;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseLiteralStringExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseOptionalExpList;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseTableConstructorExp;
import static com.github.anilople.javalua.compiler.parser.ToLuaAstLocationConverter.convert;

/**
 * @author wxq
 */
public class LuaParser {

  /**
   * token -> ast
   */
  public static Block parse(LuaLexer lexer) {
    return parseBlock(lexer);
  }

  /**
   * block ::= {stat} [retstat]
   */
  static Block parseBlock(LuaLexer lexer) {
    List<Stat> statList = LuaStatParser.parseStatList(lexer);
    Optional<Retstat> optionalRetstat = parseOptionalRetstat(lexer);
    final LuaAstLocation luaAstLocation;
    if (!statList.isEmpty()) {
      luaAstLocation = statList.get(0).getLuaAstLocation();
    } else if (optionalRetstat.isPresent()) {
      luaAstLocation = optionalRetstat.get().getLuaAstLocation();
    } else {
      luaAstLocation = LuaAstLocation.EMPTY;
    }
    return new Block(luaAstLocation, statList, optionalRetstat);
  }

  static Optional<Retstat> parseOptionalRetstat(LuaLexer lexer) {
    throw new UnsupportedOperationException();
  }

  static Name parseName(LuaLexer lexer) {
    LuaToken token = lexer.next();
    LuaAstLocation luaAstLocation = convert(token.getLocation());
    return new Name(luaAstLocation, token.getContent());
  }

  /**
   * namelist ::= Name {‘,’ Name}
   */
  static NameList parseNameList(LuaLexer lexer) {
    final Name first;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_IDENTIFIER);
      LuaAstLocation location = convert(token);
      first = new Name(location, token.getContent());
    }
    List<Name> tail = new ArrayList<>();
    while (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_COMMA)) {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_SEP_COMMA);
      LuaAstLocation location = convert(token);
      Name name = new Name(location, token.getContent());
      tail.add(name);
    }
    return new NameList(first, tail);
  }

  /**
   * var ::=  Name | prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
   * <p>
   * prefixexp ::= var | functioncall | ‘(’ exp ‘)’
   *
   */
  static Var parseVar(LuaLexer lexer) {
    throw new UnsupportedOperationException();
  }

  /**
   * varlist ::= var {‘,’ var}
   */
  static VarList parseVarList(LuaLexer lexer) {
    Var first = parseVar(lexer);
    List<Var> tail = new ArrayList<>();
    while (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_COMMA)) {
      lexer.skip(TokenEnums.TOKEN_SEP_COMMA);
      Var var = parseVar(lexer);
      tail.add(var);
    }
    return new VarList(first, tail);
  }

  /**
   * funcname ::= Name {‘.’ Name} [‘:’ Name]
   */
  static FuncName parseFuncname(LuaLexer lexer) {
    final Name name = parseName(lexer);
    // {‘.’ Name}
    final List<Name> dotNameList = new ArrayList<>();
    while (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_DOT)) {
      lexer.skip(TokenEnums.TOKEN_SEP_DOT);
      Name nameAfterDot = parseName(lexer);
      dotNameList.add(nameAfterDot);
    }

    // [‘:’ Name]
    Optional<Name> optionalColonName;
    if (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_COLON)) {
      lexer.skip(TokenEnums.TOKEN_SEP_COLON);
      optionalColonName = Optional.of(parseName(lexer));
    } else {
      optionalColonName = Optional.empty();
    }

    return new FuncName(name, dotNameList, optionalColonName);
  }

  /**
   * funcbody ::= ‘(’ [parlist] ‘)’ block end
   */
  static FuncBody parseFuncBody(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_SEP_LPAREN);
      location = convert(token);
    }
    final Optional<ParList> optionalParList = parseOptionalParList(lexer);
    final Block block = parseBlock(lexer);
    return new FuncBody(location, optionalParList, block);
  }

  static Optional<ParList> parseOptionalParList(LuaLexer lexer) {
    throw new UnsupportedOperationException();
  }

  /**
   * args ::=  ‘(’ [explist] ‘)’ | tableconstructor | LiteralString
   */
  static Args parseArgs(LuaLexer lexer) {
    if (lexer.lookAheadTest(TokenEnums.TOKEN_SEP_LPAREN)) {
      return parseExpListArgs(lexer);
    }
    if (lexer.lookAheadTest(TokenEnums.TOKEN_STRING)) {
      return parseLiteralStringArgs(lexer);
    }
    return parseTableConstructorArgs(lexer);
  }

  /**
   * ‘(’ [explist] ‘)’
   */
  static Args.ExpListArgs parseExpListArgs(LuaLexer lexer) {
    final LuaAstLocation location;
    {
      LuaToken token = lexer.skip(TokenEnums.TOKEN_SEP_LPAREN);
      location = convert(token);
    }
    Optional<ExpList> optionalExpList = parseOptionalExpList(lexer);
    return new ExpListArgs(location, optionalExpList);
  }

  static Args.LiteralStringArgs parseLiteralStringArgs(LuaLexer lexer) {
    LiteralStringExp literalStringExp = parseLiteralStringExp(lexer);
    return new LiteralStringArgs(literalStringExp);
  }

  /**
   * tableconstructor ::= ‘{’ [fieldlist] ‘}’
   */
  static Args.TableConstructorArgs parseTableConstructorArgs(LuaLexer lexer) {
    TableConstructorExp tableConstructorExp = parseTableConstructorExp(lexer);
    return new TableConstructorArgs(tableConstructorExp);
  }

  /**
   * unop ::= ‘-’ | not | ‘#’ | ‘~’
   */
  static Unop parseUnop(LuaLexer lexer) {
    LuaToken token = lexer.next();
    TokenEnums kind = token.getKind();
    LuaAstLocation location = convert(token);
    switch (kind) {
      case TOKEN_OP_MINUS:
        return new MinusUnop(location);
      case TOKEN_OP_NOT:
        return new NotUnop(location);
      case TOKEN_OP_LEN:
        return new LengthUnop(location);
      case TOKEN_OP_BNOT:
        return new BitNotUnop(location);
      default:
        throw new IllegalStateException("unknown unary op " + kind);
    }
  }

  /**
   * <pre>
   * 	binop ::=  ‘+’ | ‘-’ | ‘*’ | ‘/’ | ‘//’ | ‘^’ | ‘%’ |
   * 		 ‘&’ | ‘~’ | ‘|’ | ‘>>’ | ‘<<’ | ‘..’ |
   * 		 ‘<’ | ‘<=’ | ‘>’ | ‘>=’ | ‘==’ | ‘~=’ |
   * 		 and | or
   * </pre>
   */
  static Binop parseBinop(LuaLexer lexer) {
    LuaToken token = lexer.next();
    LuaAstLocation location = convert(token);
    return new Binop(location, token.getContent());
  }
}
