package com.github.anilople.javalua.util;

import java.util.Iterator;
import java.util.List;
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

  /**
   * 预览接下来的n个元素
   * @throws IllegalArgumentException 如果 n < 0
   * @throws NoSuchElementException 如果剩余的元素数量达不到 n 个
   */
  List<T> previewNext(int n);

  static <T> CachedIterator<T> newCachedIterator(Iterator<T> iterator) {
    return new CachedIteratorImpl<>(iterator);
  }
}
