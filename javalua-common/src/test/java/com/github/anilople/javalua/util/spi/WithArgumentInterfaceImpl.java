package com.github.anilople.javalua.util.spi;

/**
 * @author wxq
 */
public class WithArgumentInterfaceImpl implements WithArgumentInterface {

  private final int n;
  private final WithArgumentInterface next;

  public WithArgumentInterfaceImpl() {
    throw new UnsupportedOperationException();
  }

  public WithArgumentInterfaceImpl(int n, WithArgumentInterface next) {
    this.n = n;
    this.next = next;
  }

  @Override
  public int getN() {
    return this.n;
  }

  @Override
  public WithArgumentInterface getNext() {
    return this.next;
  }
}
