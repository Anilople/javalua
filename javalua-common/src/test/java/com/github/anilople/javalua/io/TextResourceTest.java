package com.github.anilople.javalua.io;

import com.github.anilople.javalua.util.CharacterUtils;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxq
 */
class TextResourceTest {

  @Test
  void previewNextChars() {
    TextResource textResource = new TextResource("1234");
    assertEquals("1", textResource.previewNextChars(1));
    assertEquals("1234", textResource.previewNextChars(4));
    assertThrows(NoSuchElementException.class, () -> textResource.previewNextChars(5));
  }

  @Test
  void previewUntilNot() {
    assertEquals("", new TextResource("").previewPrefixMatch(CharacterUtils::isLetter));
    assertEquals("a", new TextResource("a").previewPrefixMatch(CharacterUtils::isLetter));
    assertEquals("a", new TextResource("a b").previewPrefixMatch(CharacterUtils::isLetter));
    assertEquals("ab", new TextResource("ab\nc").previewPrefixMatch(CharacterUtils::isLetter));

    assertEquals("1", new TextResource("1ab\nc").previewPrefixMatch(CharacterUtils::isDigit));
    assertEquals("ab", new TextResource("ab1\nc").previewPrefixMatch(CharacterUtils::isLetter));
    assertEquals("a", new TextResource("a =1\nc").previewPrefixMatch(CharacterUtils::isLetter));
    assertEquals("a", new TextResource("a=1\nc").previewPrefixMatch(CharacterUtils::isLetter));

  }
}