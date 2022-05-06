package com.github.anilople.javalua.util.spi;

/**
 * @author wxq
 */
public class MultipleImplInterfaceImpl2 implements MultipleImplInterface {

  static {
    System.out.println("load class " + MultipleImplInterfaceImpl2.class);
  }
}
