package com.github.anilople.javalua.chunk;

import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LocVar {
  String varName;
  int startPC;
  int endPC;
}
