package com.github.anilople.javalua.compiler.lexer.enums;

/**
 * @author wxq
 */
public enum TokenEnums {
  /**
   * end-of-file
   */
  TOKEN_EOF(0, ""),
  TOKEN_VARARG(1, "..."),
  TOKEN_SEP_SEMI(2, ";"),
  TOKEN_SEP_COMMA(3, ","),
  TOKEN_SEP_DOT(4, "."),
  TOKEN_SEP_COLON(5, ":"),
  TOKEN_SEP_LABEL(6, "::"),
  TOKEN_SEP_LPAREN(7, "("),
  TOKEN_SEP_RPAREN(8, ")"),
  TOKEN_SEP_LBRACK(9, "["),
  TOKEN_SEP_RBRACK(10, "]"),
  TOKEN_SEP_LCURLY(11, "{"),
  TOKEN_SEP_RCURLY(12, "}"),
  TOKEN_OP_ASSIGN(13, "="),
  /**
   * (sub or unm)
   */
  TOKEN_OP_MINUS(14, "-"),
  /**
   * (bnot or bxor)
   */
  TOKEN_OP_WAVE(15, "~"),
  TOKEN_OP_ADD(16, "+"),
  TOKEN_OP_MUL(17, "*"),
  TOKEN_OP_DIV(18, "/"),
  TOKEN_OP_IDIV(19, "//"),
  TOKEN_OP_POW(20, "^"),
  TOKEN_OP_MOD(21, "%"),
  TOKEN_OP_BAND(22, "&"),
  TOKEN_OP_BOR(23, "|"),
  TOKEN_OP_SHR(24, ">>"),
  TOKEN_OP_SHL(25, "<<"),
  TOKEN_OP_CONCAT(26, ".."),
  TOKEN_OP_LT(27, "<"),
  TOKEN_OP_LE(28, "<="),
  TOKEN_OP_GT(29, ">"),
  TOKEN_OP_GE(30, ">="),
  TOKEN_OP_EQ(31, "=="),
  TOKEN_OP_NE(32, "~="),
  TOKEN_OP_LEN(33, "#"),
  TOKEN_OP_AND(34, "and"),
  TOKEN_OP_OR(35, "or"),
  TOKEN_OP_NOT(36, "not"),
  TOKEN_KW_BREAK(37, "break"),
  TOKEN_KW_DO(38, "do"),
  TOKEN_KW_ELSE(39, "else"),
  TOKEN_KW_ELSEIF(40, "elseif"),
  TOKEN_KW_END(41, "end"),
  TOKEN_KW_FALSE(42, "false"),
  TOKEN_KW_FOR(43, "for"),
  TOKEN_KW_FUNCTION(44, "function"),
  TOKEN_KW_GOTO(45, "goto"),
  TOKEN_KW_IF(46, "if"),
  TOKEN_KW_IN(47, "in"),
  TOKEN_KW_LOCAL(48, "local"),
  TOKEN_KW_NIL(49, "nil"),
  TOKEN_KW_REPEAT(50, "repeat"),
  TOKEN_KW_RETURN(51, "return"),
  TOKEN_KW_THEN(52, "then"),
  TOKEN_KW_TRUE(53, "true"),
  TOKEN_KW_UNTIL(54, "until"),
  TOKEN_KW_WHILE(55, "while"),
  TOKEN_IDENTIFIER(56, ""),
  /**
   * number literal
   */
  TOKEN_NUMBER(57, ""),
  /**
   * string literal
   */
  TOKEN_STRING(58, ""),
  ;
  /**
   * unary minus
   * @see #TOKEN_OP_MINUS
   */
  public static final TokenEnums TOKEN_OP_UNM = TOKEN_OP_MINUS;
  /**
   * unary minus
   * @see #TOKEN_OP_MINUS
   */
  public static final TokenEnums TOKEN_OP_SUB = TOKEN_OP_MINUS;
  /**
   * @see #TOKEN_OP_WAVE
   */
  public static final TokenEnums TOKEN_OP_BNOT = TOKEN_OP_WAVE;
  /**
   * @see #TOKEN_OP_WAVE
   */
  public static final TokenEnums TOKEN_OP_BXOR = TOKEN_OP_WAVE;
  private final int offset;
  private final String content;

  TokenEnums(int offset, String content) {
    this.offset = offset;
    this.content = content;
  }

  public int getOffset() {
    return offset;
  }

  public String getContent() {
    return content;
  }

  public static TokenEnums fromKeyword(String keyword) {
    switch (keyword) {
      case "and":
        return TOKEN_OP_AND;
      case "break":
        return TOKEN_KW_BREAK;
      case "do":
        return TOKEN_KW_DO;
      case "else":
        return TOKEN_KW_ELSE;
      case "elseif":
        return TOKEN_KW_ELSEIF;
      case "end":
        return TOKEN_KW_END;
      case "false":
        return TOKEN_KW_FALSE;
      case "for":
        return TOKEN_KW_FOR;
      case "function":
        return TOKEN_KW_FUNCTION;
      case "goto":
        return TOKEN_KW_GOTO;
      case "if":
        return TOKEN_KW_IF;
      case "in":
        return TOKEN_KW_IN;
      case "local":
        return TOKEN_KW_LOCAL;
      case "nil":
        return TOKEN_KW_NIL;
      case "not":
        return TOKEN_OP_NOT;
      case "or":
        return TOKEN_OP_OR;
      case "repeat":
        return TOKEN_KW_REPEAT;
      case "return":
        return TOKEN_KW_RETURN;
      case "then":
        return TOKEN_KW_THEN;
      case "true":
        return TOKEN_KW_TRUE;
      case "until":
        return TOKEN_KW_UNTIL;
      case "while":
        return TOKEN_KW_WHILE;
      default:
        throw new IllegalArgumentException("keyword '" + keyword + "'");
    }
  }

  /**
   * 根据单个字符串，找到对应的token
   */
  public static TokenEnums fromSingleChar(char c) {
    TokenEnums[] array = TokenEnums.values();
    for (TokenEnums tokenEnums : array) {
      String content = tokenEnums.getContent();
      if (content.length() == 1) {
        if (content.charAt(0) == c) {
          return tokenEnums;
        }
      }
    }
    return null;
  }
}
