package com.github.anilople.javalua.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxq
 */
class CachedIteratorTest {

  @Test
  void testEmpty() {
    CachedIterator<Integer> cachedIterator = CachedIterator.newCachedIterator(Collections.emptyIterator());
    assertFalse(cachedIterator.hasNext());
    assertThrows(NoSuchElementException.class, cachedIterator::previewNext);
  }

  @Test
  void testOneElement() {
    CachedIterator<Integer> cachedIterator = CachedIterator.newCachedIterator(Collections.singleton(999).iterator());
    assertTrue(cachedIterator.hasNext());
    assertEquals(999, cachedIterator.previewNext());
    assertEquals(999, cachedIterator.previewNext());
    assertEquals(999, cachedIterator.previewNext());
    assertEquals(999, cachedIterator.next());
    assertFalse(cachedIterator.hasNext());
    assertThrows(NoSuchElementException.class, cachedIterator::previewNext);
    assertThrows(NoSuchElementException.class, cachedIterator::next);
  }

  @Test
  void testTwoElement() {
    CachedIterator<Integer> cachedIterator = CachedIterator.newCachedIterator(Arrays.asList(111, 222).iterator());
    assertTrue(cachedIterator.hasNext());
    assertEquals(111, cachedIterator.previewNext());
    assertEquals(111, cachedIterator.previewNext());
    assertEquals(111, cachedIterator.next());
    assertEquals(222, cachedIterator.next());
    assertFalse(cachedIterator.hasNext());
    assertThrows(NoSuchElementException.class, cachedIterator::previewNext);
    assertThrows(NoSuchElementException.class, cachedIterator::next);
  }
}