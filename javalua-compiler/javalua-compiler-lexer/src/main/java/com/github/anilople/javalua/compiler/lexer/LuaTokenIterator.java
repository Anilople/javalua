package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_IDENTIFIER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_NUMBER;
import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_STRING;

import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import com.github.anilople.javalua.constant.PatternConstants;
import com.github.anilople.javalua.util.CharacterUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wxq
 */
class LuaTokenIterator implements Iterator<LuaToken> {

  private final LuaResource resource;

  private static final List<TokenEnums> TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC;

  static {
    TokenEnums[] array = TokenEnums.values();
    Comparator<TokenEnums> compareByLength =
        (o1, o2) -> o2.getContent().length() - o1.getContent().length();
    Arrays.sort(array, compareByLength);
    TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC = List.of(array);
  }

  public LuaTokenIterator(LuaResource resource) {
    this.resource = resource;
  }

  LuaTokenLocation getTokenLocation() {
    return new LuaTokenLocation(
        this.resource.getSourceCodeFileName(),
        this.resource.getCurrentLineNumber(),
        this.resource.getCurrentLineColumnOffset());
  }

  private LuaToken getLuaToken(TokenEnums tokenEnums) {
    return new LuaToken(this.getTokenLocation(), tokenEnums);
  }

  TokenEnums getTokenEnumsByMaxPrefixMatch() {
    for (TokenEnums tokenEnums : TOKEN_ENUMS_BY_CONTENT_LENGTH_DESC) {
      String content = tokenEnums.getContent();
      if (!content.isEmpty()) {
        if (this.resource.test(content)) {
          this.resource.skipChars(content.length());
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
    if (this.resource.test("[[")) {
      return true;
    }
    if (this.resource.test("[=")) {
      throw new UnsupportedOperationException("[=" + this.resource.getCurrentLineNumber());
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
    if (this.resource.test("'")) {
      return true;
    }
    if (this.resource.test("\"")) {
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
    char c = this.resource.previewNextChar();
    if (CharacterUtils.isDigit(c)) {
      return true;
    }
    if (c == '.') {
      if (this.resource.hasNextChars(2)) {
        String s = this.resource.previewNextChars(2);
        return CharacterUtils.isDigit(s.charAt(1));
      }
    }

    return false;
  }

  boolean isMatchPrefix(Pattern pattern) {
    Matcher matcher = pattern.matcher(this.resource);
    return matcher.find();
  }

  String scanByPattern(Pattern pattern) {
    Matcher matcher = pattern.matcher(this.resource);
    if (!matcher.find()) {
      throw new IllegalStateException("cannot find by pattern " + pattern);
    }
    String content = matcher.group();
    if (content.isEmpty()) {
      throw new IllegalStateException("cannot get content by pattern " + pattern);
    }
    this.resource.skipChars(content.length());
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
    char c = this.resource.previewNextChar();
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
    while (this.resource.hasNextChar()) {
      final char c = this.resource.previewNextChar();
      if (CharacterUtils.isLetter(c)) {
        identifier.append(c);
        this.resource.skipChars(1);
      } else if (CharacterUtils.isDigit(c)) {
        identifier.append(c);
        this.resource.skipChars(1);
      } else if (c == '_') {
        identifier.append(c);
        this.resource.skipChars(1);
      } else {
        break;
      }
    }
    return new LuaToken(this.getTokenLocation(), TOKEN_IDENTIFIER, identifier.toString());
  }

  @Override
  public boolean hasNext() {
    return this.resource.hasNextChar();
  }

  @Override
  public LuaToken next() {
    this.skipWhiteSpaces();
    if (!this.resource.hasNextChar()) {
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

    throw new IllegalStateException("" + this.resource);
  }

  /**
   * 跳过无关内容，包括注释在内
   */
  void skipWhiteSpaces() {
    while (this.resource.hasNextChar()) {
      if (this.resource.test("--")) {
        this.skipComment();
      } else if (this.resource.test("\r\n")) {
        this.resource.nextChar();
        this.resource.nextChar();
      } else if (this.resource.test("\n\r")) {
        this.resource.nextChar();
        this.resource.nextChar();
      } else if (CharacterUtils.isNewLine(this.resource.previewNextChar())) {
        this.resource.nextChar();
      } else if (CharacterUtils.isWhiteSpace(this.resource.previewNextChar())) {
        this.resource.nextChar();
      } else {
        break;
      }
    }
  }

  void skipComment() {
    if (!this.resource.test("--")) {
      throw new IllegalStateException();
    }
    this.resource.nextChar();
    this.resource.nextChar();
    if (this.resource.test("[")) {
      // long comment
      // TODO
      throw new UnsupportedOperationException("skip long comment");
    }

    // short comment
    while (this.resource.hasNextChar()
        && !CharacterUtils.isWhiteSpace(this.resource.previewNextChar())) {
      this.resource.nextChar();
    }
  }
}
