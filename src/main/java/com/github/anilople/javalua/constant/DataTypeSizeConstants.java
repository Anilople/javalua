package com.github.anilople.javalua.constant;

/**
 * @author wxq
 */
public interface DataTypeSizeConstants {

  interface Java {

    int BYTE = 1;
    int SHORT = 2;
    int INT = 4;
    int LONG = 8;
    int FLOAT = INT;
    int DOUBLE = LONG;
  }

  interface C {

    int INT = 4;
    int LONG_LONG = 8;
    int SIZE_T = 8;
  }

  interface Lua {

    int INTEGER = C.LONG_LONG;
    int NUMBER = 8;
  }
}
