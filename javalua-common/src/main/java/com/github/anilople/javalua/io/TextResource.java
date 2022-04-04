package com.github.anilople.javalua.io;

import java.util.NoSuchElementException;
import lombok.Data;

/**
 * 文本资源，一个大字符串
 * 
 * @author wxq
 */
@Data
public class TextResource implements CharSequence {

  /**
   * 原始内容
   */
  private final String content;

  /**
   * 原始内容的偏移，不计算行号
   */
  private int index;

  /**
   * 当前行号
   */
  private int currentLineNumber = 1;
  /**
   * 当前这一行的偏移
   */
  private int currentLineColumnOffset;
  
  public TextResource(String content) {
    this.content = content;
  }
  @Override
  public int length() {
    return this.content.length() - this.index;
  }

  @Override
  public char charAt(int index) {
    int indexResolved = this.index + index;
    return this.content.charAt(indexResolved);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    int startResolved = this.index + start;
    int endResolved = this.index + end;
    return this.content.subSequence(startResolved, endResolved);
  }


  public boolean hasNextChar() {
    return this.index < this.content.length();
  }

  public boolean hasNextChars(int n) {
    return this.index + n <= this.content.length();
  }

  /**
   * 不会影响状态，判断接下来的内容，是否符合想要的
   *
   * @return true如果接下来的内容以符合content
   */
  public boolean test(String content) {
    int endIndex = this.index + content.length();
    if (endIndex > this.content.length()) {
      return false;
    }
    String subString = this.content.substring(this.index, endIndex);
    return subString.equals(content);
  }

  public String previewNextChars(int n) {
    if (!this.hasNextChars(n)) {
      throw new NoSuchElementException("previewNextChars " + n);
    }
    int endIndex = this.index + n;
    return this.content.substring(this.index, endIndex);
  }

  /**
   * 使用前需要通过{@link #hasNextChar()}进行判断
   *
   * 返回下一个字符，不前移动
   *
   * @return 下一个字符
   * @throws java.util.NoSuchElementException 如果没有下一个字符了
   */
  public char previewNextChar() {
    if (!this.hasNextChar()) {
      throw new NoSuchElementException();
    }
    return this.content.charAt(this.index);
  }

  /**
   * 使用前需要通过{@link #hasNextChar()}进行判断
   *
   * 返回下一个字符，并往前移动
   *
   * @return 下一个字符
   * @throws java.util.NoSuchElementException 如果没有下一个字符了
   */
  public char nextChar() {
    char c = this.previewNextChar();
    this.index++;
    if ('\n' == c) {
      this.currentLineNumber++;
      this.currentLineColumnOffset = 0;
    } else {
      this.currentLineColumnOffset++;
    }
    return c;
  }

  public void skipChars(int n) {
    for (int i = 0; i < n; i++) {
      if (!this.hasNextChar()) {
        throw new IllegalStateException();
      }
      this.nextChar();
    }
  }

}
