package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class CachedIteratorTest {

  @Test
  void testEmpty() {
    CachedIterator<Integer> cachedIterator =
        CachedIterator.newCachedIterator(Collections.emptyIterator());
    assertFalse(cachedIterator.hasNext());
    assertThrows(NoSuchElementException.class, cachedIterator::previewNext);
  }

  @Test
  void testOneElement() {
    CachedIterator<Integer> cachedIterator =
        CachedIterator.newCachedIterator(Collections.singleton(999).iterator());
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
    CachedIterator<Integer> cachedIterator =
        CachedIterator.newCachedIterator(Arrays.asList(111, 222).iterator());
    assertTrue(cachedIterator.hasNext());
    assertEquals(111, cachedIterator.previewNext());
    assertEquals(111, cachedIterator.previewNext());
    assertEquals(111, cachedIterator.next());
    assertEquals(222, cachedIterator.next());
    assertFalse(cachedIterator.hasNext());
    assertThrows(NoSuchElementException.class, cachedIterator::previewNext);
    assertThrows(NoSuchElementException.class, cachedIterator::next);
  }

  @Test
  void testPreviewNextCase1() {
    CachedIterator<Integer> cachedIterator =
        CachedIterator.newCachedIterator(Arrays.asList(111, 222, 333).iterator());
    assertThrows(NoSuchElementException.class, () -> cachedIterator.previewNext(4));
    assertEquals(Arrays.asList(111, 222, 333), cachedIterator.previewNext(3));

    cachedIterator.next();
    assertEquals(Arrays.asList(222, 333), cachedIterator.previewNext(2));
    assertEquals(cachedIterator.previewNext(2), cachedIterator.previewNext(2));

    cachedIterator.next();
    assertEquals(List.of(333), cachedIterator.previewNext(1));
    assertEquals(cachedIterator.previewNext(), cachedIterator.previewNext(1).get(0));

    cachedIterator.next();
    assertFalse(cachedIterator.hasNext());
  }
}
