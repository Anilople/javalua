package com.github.anilople.javalua.state;

import com.github.anilople.javalua.util.SpiUtils;
import java.util.List;

/**
 * @author wxq
 */
public interface JavaFunctionFactory {

  static JavaFunctionFactory newJavaFunctionFactory() {
    return SpiUtils.loadOneInterfaceImpl(JavaFunctionFactory.class);
  }

  List<JavaFunction> getStdlib();
}
