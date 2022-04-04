package com.github.anilople.javalua.util;

import java.util.Iterator;

/**
 * @author wxq
 */
class CachedIteratorImpl<T> implements CachedIterator<T> {

  private final Iterator<T> iterator;

  private T cachedElement;

  CachedIteratorImpl(Iterator<T> iterator) {
    this.iterator = iterator;
  }

  @Override
  public T previewNext() {
    if (null == this.cachedElement) {
      this.cachedElement = next();
    }
    return this.cachedElement;
  }

  @Override
  public boolean hasNext() {
    if (null != this.cachedElement) {
      return true;
    }
    return this.iterator.hasNext();
  }

  @Override
  public T next() {
    if (null != this.cachedElement) {
      T t = this.cachedElement;
      this.cachedElement = null;
      return t;
    }
    return this.iterator.next();
  }
}
