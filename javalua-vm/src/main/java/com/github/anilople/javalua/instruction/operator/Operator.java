package com.github.anilople.javalua.instruction.operator;

import java.util.function.BiFunction;

/**
 * @author wxq
 */
@FunctionalInterface
public interface Operator<T, U, R> {
  BiFunction<T, U, R> getOperator();
}
