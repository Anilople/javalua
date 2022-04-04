package com.github.anilople.javalua.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 可以预知下一个元素，但是又不改变迭代器的状态
 *
 * @author wxq
 */
public interface CachedIterator<T> extends Iterator<T> {

  /**
   * 预览下一个元素，与{@link #next()}不同，这个方法不会跳过这个元素
   *
   * @throws NoSuchElementException – if the iteration has no more elements
   */
  T previewNext();

  static <T> CachedIterator<T> newCachedIterator(Iterator<T> iterator) {
    return new CachedIteratorImpl<>(iterator);
  }
}
