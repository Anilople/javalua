package com.github.anilople.javalua.util;

/**
 * @author wxq
 */
public class CharacterUtils {

  public static boolean isWhiteSpace(char c) {
    return Character.isWhitespace(c);
  }

  public static boolean isNewLine(char c) {
    return c == '\r' || c == '\n';
  }

  public static boolean isDigit(char c) {
    return Character.isDigit(c);
  }

  public static boolean isLetter(char c) {
    return Character.isLetter(c);
  }
}
