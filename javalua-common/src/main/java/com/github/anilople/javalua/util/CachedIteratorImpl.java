package com.github.anilople.javalua.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author wxq
 */
class CachedIteratorImpl<T> implements CachedIterator<T> {

  private final Iterator<T> iterator;

  private final LinkedList<T> cachedElements = new LinkedList<>();

  CachedIteratorImpl(Iterator<T> iterator) {
    this.iterator = iterator;
  }

  @Override
  public T previewNext() {
    if (this.cachedElements.isEmpty()) {
      T t = next();
      this.cachedElements.add(t);
    }
    return this.cachedElements.element();
  }

  @Override
  public List<T> previewNext(int n) {
    if (n < 0) {
      throw new IllegalArgumentException(n + " < 0");
    }
    while (this.cachedElements.size() < n) {
      T t = this.iterator.next();
      this.cachedElements.add(t);
    }
    List<T> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      T t = this.cachedElements.get(i);
      list.add(t);
    }
    return Collections.unmodifiableList(list);
  }

  @Override
  public boolean hasNext() {
    if (!this.cachedElements.isEmpty()) {
      return true;
    }
    return this.iterator.hasNext();
  }

  @Override
  public T next() {
    if (!this.cachedElements.isEmpty()) {
      return this.cachedElements.pop();
    }
    return this.iterator.next();
  }
}
