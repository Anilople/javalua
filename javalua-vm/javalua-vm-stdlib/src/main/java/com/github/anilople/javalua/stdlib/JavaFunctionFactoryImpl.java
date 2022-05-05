package com.github.anilople.javalua.stdlib;

import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.JavaFunctionFactory;
import com.github.anilople.javalua.util.SpiUtils;
import java.util.List;

/**
 * @author wxq
 */
public class JavaFunctionFactoryImpl implements JavaFunctionFactory {

  @Override
  public List<JavaFunction> getStdlib() {
    return SpiUtils.loadAllInterfaceImpl(JavaFunction.class);
  }
}
