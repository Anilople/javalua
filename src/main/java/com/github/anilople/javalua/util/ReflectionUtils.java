package com.github.anilople.javalua.util;

import com.github.anilople.javalua.util.ByteUtils.DecodeRuntimeException;
import com.github.anilople.javalua.util.ByteUtils.EncodeRuntimeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxq
 */
public class ReflectionUtils {

  static List<Field> getNonStaticFields(Field[] fields) {
    List<Field> fieldList = new ArrayList<>(fields.length);
    for (Field field : fields) {
      if (!Modifier.isStatic(field.getModifiers())) {
        fieldList.add(field);
      }
    }
    return fieldList;
  }

  static List<Field> getNonStaticDeclaredFields(Class<?> clazz) {
    return getNonStaticFields(clazz.getDeclaredFields());
  }

  static Object getFieldValue(Object object, Field field) {
    field.trySetAccessible();
    final Object value;
    try {
      value = field.get(object);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("cannot get field " + field + "'s value in object " + object, e);
    }
    return value;
  }

  static int getFieldValueArrayLength(Object object, Field field) {
    Class<?> fieldType = field.getType();
    if (!isArray(fieldType)) {
      throw new IllegalArgumentException("field " + field + " isn't an array");
    }
    final Object fieldValue = getFieldValue(object, field);
    if (null == fieldValue) {
      return 0;
    }
    if (fieldValue instanceof byte[]) {
      return ((byte[]) fieldValue).length;
    }
    throw new UnsupportedOperationException("get array length of " + fieldValue);
  }

  static void setFieldValue(Object object, Field field, Object fieldValue) {
    field.trySetAccessible();
    try {
      field.set(object, fieldValue);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("cannot set field value " + fieldValue + " to object " + object, e);
    }
  }

  static boolean isPrimitive(Class<?> clazz) {
    return clazz.isPrimitive();
  }

  static boolean isArray(Class<?> clazz) {
    return clazz.isArray();
  }

  /**
   * 用默认构造方法new java对象
   */
  static <T> T newInstance(Class<T> clazz) {
    final Constructor<T> constructor;
    try {
      constructor = clazz.getConstructor();
    } catch (NoSuchMethodException e) {
      throw new DecodeRuntimeException("no arg constructor doesn't exist in class " + clazz, e);
    }

    final T object;
    try {
      object = constructor.newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new DecodeRuntimeException("cannot new instance of class " + clazz, e);
    }
    return object;
  }
}
