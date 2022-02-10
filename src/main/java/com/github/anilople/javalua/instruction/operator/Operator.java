package com.github.anilople.javalua.instruction.operator;

import java.util.function.BiFunction;

/**
 * @author wxq
 */
public interface Operator<T, U, R> {
  BiFunction<T, U, R> getOperator();
}
