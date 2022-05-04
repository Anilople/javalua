package com.github.anilople.javalua.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
   * 确保interface符合要求
   */
  static <S> void ensureInterfaceValid(Class<S> interfaceClass) {
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
  }

  static Class<?>[] toParameterTypes(Object ... initargs) {
    final int len = initargs.length;
    Class<?>[] parameterTypes = new Class[len];
    for (int i = 0; i < len; i++) {
      parameterTypes[i] = initargs[i].getClass();
    }
    return parameterTypes;
  }

  /**
   * 加载1个interface的实现，注意只允许有1个实现。
   * @param interfaceClass interface对应的class
   * @param parameterTypes 构造器的形参
   * @param initargs 构造器用的实参
   * @param <S> interface的type
   * @return interface的实现 实例
   */
  public static <S> S loadOneInterfaceImpl(Class<S> interfaceClass, Class<?>[] parameterTypes, Object[] initargs) {
    ensureInterfaceValid(interfaceClass);
    final ServiceLoader<S> serviceLoader = ServiceLoader.load(interfaceClass);
    Optional<Provider<S>> optionalProvider = serviceLoader.stream().findFirst();
    if (optionalProvider.isEmpty()) {
      throw new IllegalStateException("provider of " + interfaceClass + " is empty");
    }
    Provider<S> provider = optionalProvider.get();

    // 获取实现类
    Class<? extends S> implClass = provider.type();

    // 获取构造器
    final Constructor<? extends S> constructor;
    try {
      constructor = implClass.getConstructor(parameterTypes);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException("cannot find constructor of " + implClass
          + " with parameterTypes " + Arrays.toString(parameterTypes)
          + " you should write it", e);
    }

    try {
      return constructor.newInstance(initargs);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException("cannot newInstance of " + implClass + " with args " + Arrays.toString(
          initargs), e);
    }
  }

  /**
   * {@link #loadOneInterfaceImpl(Class, Class[], Object[])}的简化模式，不接收参数
   */
  public static <S> S loadOneInterfaceImpl(Class<S> interfaceClass) {
    return loadOneInterfaceImpl(interfaceClass, new Class[]{}, new Object[]{});
  }

  /**
   * {@link #loadOneInterfaceImpl(Class, Class[], Object[])}的简化模式，只接收1个参数
   */
  public static <S> S loadOneInterfaceImpl(Class<S> interfaceClass, Class<?> parameterType, Object initArg) {
    return loadOneInterfaceImpl(interfaceClass, new Class[]{parameterType}, new Object[]{initArg});
  }

  /**
   * {@link #loadOneInterfaceImpl(Class, Class[], Object[])}的简化模式，只接收2个参数
   */
  public static <S> S loadOneInterfaceImpl(Class<S> interfaceClass,
      Class<?> parameterType1,
      Class<?> parameterType2,
      Object initArg1,
      Object initArg2
  ) {
    return loadOneInterfaceImpl(
        interfaceClass,
        new Class[]{parameterType1, parameterType2},
        new Object[]{initArg1, initArg2}
    );
  }
}
