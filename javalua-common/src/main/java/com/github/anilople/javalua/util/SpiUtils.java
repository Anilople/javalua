package com.github.anilople.javalua.util;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

/**
 * spi 是 service provider interface 的简称。
 * <p/>
 * 本项目中用来解耦接口和具体的实现.
 * <p/>
 * 例如 有 interface com.xxx.MyInterface，有实现 com.xxx.MyInterfaceImpl，
 * 使用者需要 在 src/main/resources 下，创建文件 META-INF/services/com.xxx.MyInterface，
 * 文件里放 1 行内容 com.xxx.MyInterfaceImpl
 *
 * @see ServiceLoader
 * @author wxq
 */
public class SpiUtils {

  /**
   * 加载1个interface的实现，注意只允许有1个实现。
   * @param interfaceClass interface对应的class
   * @param <S> interface的type
   * @return interface的实现 实例
   */
  public static <S> S loadOneInterfaceImpl(Class<S> interfaceClass) {
    if (!interfaceClass.isInterface()) {
      throw new IllegalArgumentException(interfaceClass + " isn't an interface");
    }
    final ServiceLoader<S> serviceLoader = ServiceLoader.load(interfaceClass);
    final long count = serviceLoader.stream().count();
    if (count < 1) {
      throw new IllegalStateException(interfaceClass + " has no implementation");
    }
    if (count > 1) {
      List<Provider<S>> list = serviceLoader.stream().collect(Collectors.toList());
      List<Class<? extends S>> providerTypes =
          list.stream().map(Provider::type).collect(Collectors.toList());
      throw new IllegalStateException(
          interfaceClass
              + " has multiple implementations, implementation's count = "
              + list.size()
              + ", they are "
              + providerTypes);
    }
    Optional<S> optional = serviceLoader.findFirst();
    return optional.get();
  }
}
