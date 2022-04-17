package com.github.anilople.javalua.compiler.parser;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.*;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.canParseExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseLiteralStringExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseOptionalExpList;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseTableConstructorExp;
import static com.github.anilople.javalua.compiler.parser.LuaExpParser.parseVarargExp;
import static com.github.anilople.javalua.compiler.parser.LuaPrefixExpParser.canParsePrefixExp;
import static com.github.anilople.javalua.compiler.parser.LuaPrefixExpParser.parsePrefixExp;
import static com.github.anilople.javalua.compiler.parser.ToLuaAstLocationConverter.convert;

import com.github.anilople.javalua.compiler.ast.Args;
import com.github.anilople.javalua.compiler.ast.Args.ExpListArgs;
import com.github.anilople.javalua.compiler.ast.Args.LiteralStringArgs;
import com.github.anilople.javalua.compiler.ast.Args.TableConstructorArgs;
import com.github.anilople.javalua.compiler.ast.Binop;
import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.ExpList;
import com.github.anilople.javalua.compiler.ast.Field;
import com.github.anilople.javalua.compiler.ast.Field.ExpField;
import com.github.anilople.javalua.compiler.ast.Field.NameField;
import com.github.anilople.javalua.compiler.ast.Field.TableField;
import com.github.anilople.javalua.compiler.ast.FieldList;
import com.github.anilople.javalua.compiler.ast.FieldSep;
import com.github.anilople.javalua.compiler.ast.FieldSep.CommaFieldSep;
import com.github.anilople.javalua.compiler.ast.FieldSep.SemicolonFieldSep;
import com.github.anilople.javalua.compiler.ast.FuncBody;
import com.github.anilople.javalua.compiler.ast.FuncName;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Name;
import com.github.anilople.javalua.compiler.ast.NameList;
import com.github.anilople.javalua.compiler.ast.ParList;
import com.github.anilople.javalua.compiler.ast.ParList.NameListParList;
import com.github.anilople.javalua.compiler.ast.ParList.VarargParList;
import com.github.anilople.javalua.compiler.ast.Retstat;
import com.github.anilople.javalua.compiler.ast.Unop;
import com.github.anilople.javalua.compiler.ast.Unop.BitNotUnop;
import com.github.anilople.javalua.compiler.ast.Unop.LengthUnop;
import com.github.anilople.javalua.compiler.ast.Unop.MinusUnop;
import com.github.anilople.javalua.compiler.ast.Unop.NotUnop;
import com.github.anilople.javalua.compiler.ast.Var;
import com.github.anilople.javalua.compiler.ast.Var.NameVar;
import com.github.anilople.javalua.compiler.ast.Var.PrefixExpNameVar;
import com.github.anilople.javalua.compiler.ast.Var.PrefixExpVar;
import com.github.anilople.javalua.compiler.ast.VarList;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import com.github.anilople.javalua.compiler.ast.exp.LiteralStringExp;
import com.github.anilople.javalua.compiler.ast.exp.TableConstructorExp;
import com.github.anilople.javalua.compiler.ast.exp.VarargExp;
import com.github.anilople.javalua.compiler.ast.stat.Stat;
import com.github.anilople.javalua.compiler.lexer.LuaLexer;
import com.github.anilople.javalua.compiler.lexer.LuaToken;
import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wxq
 */
public class LuaParser {

  /**
   * lua code -> ast
   */
  public static Block parse(String luaCode, String sourceCodeFilePath) {
    LuaLexer lexer = LuaLexer.newLuaLexer(luaCode, sourceCodeFilePath);
    return parse(lexer);
  }

  /**
   * lua code -> ast
   */
  public static Block parse(String luaCode) {
    return parse(luaCode, "unknown");
  }

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
      luaAstLocation = statList.get(0).getLocation();
    } else if (optionalRetstat.isPresent()) {
      luaAstLocation = optionalRetstat.get().getLocation();
    } else {
      luaAstLocation = LuaAstLocation.EMPTY;
    }
    return new Block(luaAstLocation, statList, optionalRetstat);
  }

  /**
   * retstat ::= return [explist] [‘;’]
   */
  static boolean canParseRetstat(LuaLexer lexer) {
    return lexer.lookAheadTest(TOKEN_KW_RETURN);
  }

  /**
   * retstat ::= return [explist] [‘;’]
   */
  static Retstat parseRetstat(LuaLexer lexer) {
    LuaToken token = lexer.skip(TOKEN_KW_RETURN);
    LuaAstLocation location = convert(token);
    Optional<ExpList> optionalExpList = parseOptionalExpList(lexer);
    if (lexer.lookAheadTest(TOKEN_SEP_COLON)) {
      lexer.skip(TOKEN_SEP_COLON);
    }
    return new Retstat(location, optionalExpList);
  }

  static Optional<Retstat> parseOptionalRetstat(LuaLexer lexer) {
    if (canParseRetstat(lexer)) {
      Retstat retstat = parseRetstat(lexer);
      return Optional.of(retstat);
    } else {
      return Optional.empty();
    }
  }

  static boolean canParseName(LuaLexer lexer) {
    return lexer.lookAheadTest(TOKEN_IDENTIFIER);
  }

  static Name parseName(LuaLexer lexer) {
    LuaToken token = lexer.skip(TOKEN_IDENTIFIER);
    LuaAstLocation location = convert(token.getLocation());
    return new Name(location, token.getContent());
  }

  /**
   * namelist ::= Name {‘,’ Name}
   */
  static NameList parseNameList(LuaLexer lexer) {
    final Name first = parseName(lexer);
    List<Name> tail = new ArrayList<>();
    while (lexer.lookAheadTest(TOKEN_SEP_COMMA)) {
      lexer.skip(TOKEN_SEP_COMMA);
      Name name = parseName(lexer);
      tail.add(name);
    }
    return new NameList(first, tail);
  }

  static boolean canParseVar(LuaLexer lexer) {
    if (canParseName(lexer)) {
      return true;
    }
    return canParsePrefixExp(lexer);
  }

  /**
   * var ::=  Name | prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
   * <p>
   * prefixexp ::= var | functioncall | ‘(’ exp ‘)’
   *
   */
  static Var parseVar(LuaLexer lexer) {
    if (canParseName(lexer)) {
      Name name = parseName(lexer);
      return new NameVar(name);
    }
    Exp prefixExp = parsePrefixExp(lexer);
    return parseVar(lexer, prefixExp);
  }

  /**
   * var ::=  Name | prefixexp ‘[’ exp ‘]’ | prefixexp ‘.’ Name
   */
  static Var parseVar(LuaLexer lexer, Exp prefixExp) {
    if (lexer.lookAheadTest(TOKEN_SEP_LBRACK)) {
      lexer.skip(TOKEN_SEP_LBRACK);
      Exp exp = parseExp(lexer);
      lexer.skip(TOKEN_SEP_RBRACK);
      return new PrefixExpVar(prefixExp, exp);
    } else if (lexer.lookAheadTest(TOKEN_SEP_DOT)) {
      lexer.skip(TOKEN_SEP_DOT);
      Name name = parseName(lexer);
      return new PrefixExpNameVar(prefixExp, name);
    } else {
      throw new IllegalStateException("unknown token after prefixexp " + lexer);
    }
  }

  static boolean canParseVarList(LuaLexer lexer) {
    return canParseVar(lexer);
  }

  /**
   * varlist ::= var {‘,’ var}
   */
  static VarList parseVarList(LuaLexer lexer, Var first) {
    List<Var> tail = parseVarListTail(lexer);
    return new VarList(first, tail);
  }

  /**
   * varlist ::= var {‘,’ var}
   */
  static VarList parseVarList(LuaLexer lexer) {
    Var first = parseVar(lexer);
    return parseVarList(lexer, first);
  }

  static List<Var> parseVarListTail(LuaLexer lexer) {
    List<Var> tail = new ArrayList<>();
    while (lexer.lookAheadTest(TOKEN_SEP_COMMA)) {
      lexer.skip(TOKEN_SEP_COMMA);
      Var var = parseVar(lexer);
      tail.add(var);
    }
    return tail;
  }

  /**
   * funcname ::= Name {‘.’ Name} [‘:’ Name]
   */
  static FuncName parseFuncname(LuaLexer lexer) {
    final Name name = parseName(lexer);
    // {‘.’ Name}
    final List<Name> dotNameList = new ArrayList<>();
    while (lexer.lookAheadTest(TOKEN_SEP_DOT)) {
      lexer.skip(TOKEN_SEP_DOT);
      Name nameAfterDot = parseName(lexer);
      dotNameList.add(nameAfterDot);
    }

    // [‘:’ Name]
    Optional<Name> optionalColonName;
    if (lexer.lookAheadTest(TOKEN_SEP_COLON)) {
      lexer.skip(TOKEN_SEP_COLON);
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
      LuaToken token = lexer.skip(TOKEN_SEP_LPAREN);
      location = convert(token);
    }
    final Optional<ParList> optionalParList;
    if (canParseParList(lexer)) {
      ParList parList = parseParList(lexer);
      optionalParList = Optional.of(parList);
    } else {
      optionalParList = Optional.empty();
    }
    lexer.skip(TOKEN_SEP_RPAREN);
    final Block block = parseBlock(lexer);
    return new FuncBody(location, optionalParList, block);
  }

  /**
   * parlist ::= namelist [‘,’ ‘...’] | ‘...’
   * <p>
   * namelist ::= Name {‘,’ Name}
   * <p>
   */
  static boolean canParseParList(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_VARARG)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_STRING)) {
      return true;
    }
    return false;
  }

  /**
   * parlist ::= namelist [‘,’ ‘...’] | ‘...’
   */
  static ParList parseParList(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_VARARG)) {
      VarargExp varargExp = parseVarargExp(lexer);
      return new VarargParList(varargExp);
    } else {
      NameList nameList = parseNameList(lexer);
      final Optional<VarargExp> optionalVarargExp;
      if (lexer.lookAheadTest(TOKEN_SEP_COMMA)) {
        VarargExp varargExp = parseVarargExp(lexer);
        optionalVarargExp = Optional.of(varargExp);
      } else {
        optionalVarargExp = Optional.empty();
      }
      return new NameListParList(nameList, optionalVarargExp);
    }
  }

  /**
   * args ::=  ‘(’ [explist] ‘)’ | ‘{’ [fieldlist] ‘}’ | LiteralString
   */
  static boolean canParseArgs(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_LPAREN)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_SEP_LCURLY)) {
      return true;
    }
    return lexer.lookAheadTest(TOKEN_STRING);
  }

  /**
   * args ::=  ‘(’ [explist] ‘)’ | tableconstructor | LiteralString
   */
  static Args parseArgs(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_LPAREN)) {
      return parseExpListArgs(lexer);
    }
    if (lexer.lookAheadTest(TOKEN_STRING)) {
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
      LuaToken token = lexer.skip(TOKEN_SEP_LPAREN);
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

  /**
   * fieldlist ::= field {fieldsep field} [fieldsep]
   */
  static boolean canParseFieldList(LuaLexer lexer) {
    return canParseField(lexer);
  }

  /**
   * fieldlist ::= field {fieldsep field} [fieldsep]
   */
  static FieldList parseFieldList(LuaLexer lexer) {
    Field first = parseField(lexer);
    List<Field> tail = new ArrayList<>();
    while (canParseFieldSep(lexer)) {
      parseFieldSep(lexer);
      Field field = parseField(lexer);
      tail.add(field);
    }
    if (canParseFieldSep(lexer)) {
      parseFieldSep(lexer);
    }
    return new FieldList(first, tail);
  }

  /**
   * fieldlist ::= field {fieldsep field} [fieldsep]
   * <p>
   * field ::= ‘[’ exp ‘]’ ‘=’ exp | Name ‘=’ exp | exp
   */
  static Optional<FieldList> parseOptionalFieldList(LuaLexer lexer) {
    if (canParseFieldList(lexer)) {
      FieldList fieldList = parseFieldList(lexer);
      return Optional.of(fieldList);
    } else {
      return Optional.empty();
    }
  }

  /**
   * field ::= ‘[’ exp ‘]’ ‘=’ exp | Name ‘=’ exp | exp
   */
  static boolean canParseField(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_LBRACK)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_STRING)) {
      return true;
    }
    if (canParseExp(lexer)) {
      return true;
    }
    return false;
  }

  /**
   * field ::= ‘[’ exp ‘]’ ‘=’ exp | Name ‘=’ exp | exp
   */
  static Field parseField(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_LBRACK)) {
      // ‘[’
      LuaToken token = lexer.skip(TOKEN_SEP_LBRACK);
      LuaAstLocation location = convert(token);
      Exp expInSquare = parseExp(lexer);
      // ‘]’
      lexer.skip(TOKEN_SEP_RBRACK);
      // ‘=’
      lexer.skip(TOKEN_OP_ASSIGN);
      Exp exp = parseExp(lexer);
      return new TableField(location, expInSquare, exp);
    } else if (canParseName(lexer)) {
      Name name = parseName(lexer);
      // ‘=’
      lexer.skip(TOKEN_OP_ASSIGN);
      Exp exp = parseExp(lexer);
      return new NameField(name, exp);
    } else {
      Exp exp = parseExp(lexer);
      return new ExpField(exp);
    }
  }

  /**
   * fieldsep ::= ‘,’ | ‘;’
   */
  static boolean canParseFieldSep(LuaLexer lexer) {
    if (lexer.lookAheadTest(TOKEN_SEP_COMMA)) {
      return true;
    }
    if (lexer.lookAheadTest(TOKEN_SEP_SEMI)) {
      return true;
    }
    return false;
  }

  /**
   * fieldsep ::= ‘,’ | ‘;’
   */
  static FieldSep parseFieldSep(LuaLexer lexer) {
    LuaToken token = lexer.next();
    LuaAstLocation location = convert(token);
    TokenEnums kind = token.getKind();
    if (TOKEN_SEP_COMMA.equals(kind)) {
      return new CommaFieldSep(location);
    }
    if (TOKEN_SEP_SEMI.equals(kind)) {
      return new SemicolonFieldSep(location);
    }
    throw new IllegalStateException("token " + token);
  }
}
