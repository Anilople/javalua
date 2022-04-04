package com.github.anilople.javalua.constant;

import java.util.regex.Pattern;

/**
 * 正则匹配
 *
 * @author wxq
 */
public interface PatternConstants {

  /**
   * lua的数字
   */
  Pattern LUA_NUMBER =
      Pattern.compile(
          "^0[xX][0-9a-fA-F]*(\\.[0-9a-fA-F]*)?([pP][+\\-]?[0-9]+)?|^[0-9]*(\\.[0-9]*)?([eE][+\\-]?[0-9]+)?");

  Pattern SHORT_STRING =
      Pattern.compile(
          "(?s)(^'(\\\\\\\\|\\\\'|\\\\\\n|\\\\z\\s*|[^'\\n])*')|(^\"(\\\\\\\\|\\\\\"|\\\\\\n|\\\\z\\s*|[^\"\\n])*\")");
}
