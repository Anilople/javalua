package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.compiler.lexer.LuaToken.TokenLocation;
import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import com.github.anilople.javalua.constant.PatternConstants;
import com.github.anilople.javalua.util.CharacterUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_IDENTIFIER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_NUMBER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_STRING;

/**
 * @author wxq
 */
class LuaLexerImpl implements LuaLexer {

  private final LuaResource luaResource;

  private static final List<TokenEnums> TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC;

  /**
   * 为了{@link #lookAhead()}而设计
   */
  private final Queue<LuaToken> tokenCache = new LinkedList<>();

  static {
    TokenEnums[] array = TokenEnums.values();
    Comparator<TokenEnums> compareByLength = (o1, o2) ->
        o2.getContent().length() - o1.getContent().length();
    Arrays.sort(array, compareByLength);
    TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC = List.of(array);
  }

  public LuaLexerImpl(String luaCode) {
    this.luaResource = new LuaResource(luaCode, "unknown");
  }



  /**
   * 发生了错误
   */
  void error(TokenEnums process) {
    throw new IllegalStateException(
        "process token " + process
            + " file " + this.luaResource.getSourceCodeFileName()
            + " line " + this.luaResource.getCurrentLineNumber()
            + " column " + this.luaResource.getCurrentLineColumnOffset()
    );
  }

  TokenLocation getTokenLocation() {
    return new TokenLocation(this.luaResource);
  }

  private LuaToken getLuaToken(TokenEnums tokenEnums) {
    return new LuaToken(this.getTokenLocation(), tokenEnums);
  }

  TokenEnums getTokenEnumsByMaxPrefixMatch() {
    for (TokenEnums tokenEnums : TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC) {
      String content = tokenEnums.getContent();
      if (!content.isEmpty()) {
        if (this.luaResource.test(content)) {
          this.luaResource.skipChars(content.length());
          return tokenEnums;
        }
      }
    }
    return null;
  }

  /**
   * page 260
   *
   * 转义处理
   */
  static String escape(String content) {
    // TODO
    return content;
  }

  boolean isLongString() {
    if (this.luaResource.test("[[")) {
      return true;
    }
    if (this.luaResource.test("[=")) {
      throw new UnsupportedOperationException("[=" + this.luaResource.getCurrentLineNumber());
    }
    return false;
  }

  /**
   * page 258
   */
  LuaToken scanLongString() {
    throw new UnsupportedOperationException();
  }

  boolean isShortString() {
    if (this.luaResource.test("'")) {
      return true;
    }
    if (this.luaResource.test("\"")) {
      return true;
    }
    return false;
  }

  /**
   * page 259
   */
  LuaToken scanShortString() {
    String content = this.scanByPattern(PatternConstants.SHORT_STRING);
    // 去除2侧的单引号或者双引号
    String stringContent = content.substring(1, content.length() - 1);
    String stringContentEscaped = escape(stringContent);
    return new LuaToken(this.getTokenLocation(), TOKEN_STRING, stringContentEscaped);
  }

  /**
   * page 262
   *
   * 数字开头或者小数点开头
   */
  boolean isNumber() {
    char c = this.luaResource.previewNextChar();
    if (CharacterUtils.isDigit(c)) {
      return true;
    }
    if (c == '.') {
      if (this.luaResource.hasNextChars(2)) {
        String s = this.luaResource.previewNextChars(2);
        return CharacterUtils.isDigit(s.charAt(1));
      }
    }

    return false;
  }

  boolean isMatchPrefix(Pattern pattern) {
    Matcher matcher = pattern.matcher(this.luaResource);
    return matcher.find();
  }

  String scanByPattern(Pattern pattern) {
    Matcher matcher = pattern.matcher(this.luaResource);
    if (!matcher.find()) {
      throw new IllegalStateException("cannot find by pattern " + pattern);
    }
    String content = matcher.group();
    if (content.isEmpty()) {
      throw new IllegalStateException("cannot get content by pattern " + pattern);
    }
    this.luaResource.skipChars(content.length());
    return content;
  }

  /**
   * page 262
   */
  LuaToken scanNumber() {
    String content = this.scanByPattern(PatternConstants.LUA_NUMBER);
    return new LuaToken(this.getTokenLocation(), TOKEN_NUMBER, content);
  }

  /**
   * Names (also called identifiers) in Lua can be
   *
   * any string of letters, digits, and underscores,
   *
   * not beginning with a digit
   */
  boolean isIdentifier() {
    char c = this.luaResource.previewNextChar();
    if (c == '_') {
      return true;
    }
    if (CharacterUtils.isLetter(c)) {
      return true;
    }
    return false;
  }

  LuaToken scanIdentifier() {
    StringBuilder identifier = new StringBuilder();
    while (this.luaResource.hasNextChar()) {
      final char c = this.luaResource.previewNextChar();
      if (CharacterUtils.isLetter(c)) {
        identifier.append(c);
        this.luaResource.skipChars(1);
      } else if (CharacterUtils.isDigit(c)) {
        identifier.append(c);
        this.luaResource.skipChars(1);
      } else if (c == '_') {
        identifier.append(c);
        this.luaResource.skipChars(1);
      } else {
        break;
      }
    }
    return new LuaToken(this.getTokenLocation(), TOKEN_IDENTIFIER, identifier.toString());
  }

  public LuaToken nextToken() {
    if (!this.tokenCache.isEmpty()) {
      // cache 里有，直接拿
      return this.tokenCache.poll();
    }

    this.skipWhiteSpaces();
    if (!this.luaResource.hasNextChar()) {
      return this.getLuaToken(TOKEN_EOF);
    }

    // long string
    if (this.isLongString()) {
      return this.scanLongString();
    }

    // short string
    if (this.isShortString()) {
      return this.scanShortString();
    }

    // number
    if (this.isNumber()) {
      return this.scanNumber();
    }

    // 关键字
    {
      TokenEnums tokenEnums = this.getTokenEnumsByMaxPrefixMatch();
      if (null != tokenEnums) {
        return this.getLuaToken(tokenEnums);
      }
    }

    // 标识符
    if (this.isIdentifier()) {
      return this.scanIdentifier();
    }

    throw new IllegalStateException();
  }

  /**
   * page 264
   *
   * 查看下一个token是什么，不会跳过这个token
   */
  public LuaToken lookAhead() {
    if (this.tokenCache.isEmpty()) {
      LuaToken token = this.nextToken();
      this.tokenCache.add(token);
    }
    return this.tokenCache.element();
  }

  /**
   * 跳过无关内容，包括注释在内
   */
  void skipWhiteSpaces() {
    while (this.luaResource.hasNextChar()) {
      if (this.luaResource.test("--")) {
        this.skipComment();
      } else if (this.luaResource.test("\r\n")) {
        this.luaResource.nextChar();
        this.luaResource.nextChar();
      } else if (this.luaResource.test("\n\r")) {
        this.luaResource.nextChar();
        this.luaResource.nextChar();
      } else if (CharacterUtils.isNewLine(this.luaResource.previewNextChar())) {
        this.luaResource.nextChar();
      } else if (CharacterUtils.isWhiteSpace(this.luaResource.previewNextChar())) {
        this.luaResource.nextChar();
      } else {
        break;
      }
    }
  }

  void skipComment() {
    if (!this.luaResource.test("--")) {
      throw new IllegalStateException();
    }
    this.luaResource.nextChar();
    this.luaResource.nextChar();
    if (this.luaResource.test("[")) {
      // long comment
      // TODO
      throw new UnsupportedOperationException("skip long comment");
    }

    // short comment
    while (this.luaResource.hasNextChar()
        && !CharacterUtils.isWhiteSpace(this.luaResource.previewNextChar())) {
      this.luaResource.nextChar();
    }
  }

  @Override
  public LuaToken previewNext() {
    return null;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public LuaToken next() {
    return null;
  }
}
