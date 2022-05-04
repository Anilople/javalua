package com.github.anilople.javalua.util.spi;

/**
 * @author wxq
 */
public class MultipleImplInterfaceImpl1 implements MultipleImplInterface {

  static {
    System.out.println("load class " + MultipleImplInterfaceImpl1.class);
  }
}
