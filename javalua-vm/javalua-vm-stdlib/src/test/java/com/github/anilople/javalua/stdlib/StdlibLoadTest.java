package com.github.anilople.javalua.stdlib;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.JavaFunctionFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class StdlibLoadTest {

  @Test
  void load() {
    JavaFunctionFactory javaFunctionFactory = JavaFunctionFactory.newJavaFunctionFactory();
    List<JavaFunction> javaFunctions = javaFunctionFactory.getStdlib();
    System.out.println(javaFunctions.size());
    assertTrue(javaFunctions.size() > 0);
  }
}
