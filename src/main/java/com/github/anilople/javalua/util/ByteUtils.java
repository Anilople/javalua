package com.github.anilople.javalua.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用 大端 bigEndian 处理 byte 数组
 *
 * @author wxq
 */
public class ByteUtils {

  /**
   * value为null时，对应的byte数组
   */
  private static final Map<Class<?>, byte[]> PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY;

  static {
    // see https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
    Map<Class<?>, byte[]> primitiveType2emptyByteArray = new HashMap<>();
    primitiveType2emptyByteArray.put(Byte.TYPE, new byte[1]);
    primitiveType2emptyByteArray.put(Short.TYPE, new byte[2]);
    primitiveType2emptyByteArray.put(Integer.TYPE, new byte[4]);
    primitiveType2emptyByteArray.put(Long.TYPE, new byte[8]);
    primitiveType2emptyByteArray.put(Float.TYPE, new byte[4]);
    primitiveType2emptyByteArray.put(Double.TYPE, new byte[8]);

    primitiveType2emptyByteArray.put(Character.TYPE, new byte[2]);

    PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY = Collections.unmodifiableMap(primitiveType2emptyByteArray);
  }

  static byte[] getEmptyByteArrayOf(Class<?> clazz) {
    if (clazz.isPrimitive()) {
      throw new IllegalArgumentException("type " + clazz + " isn't primitive type");
    }
    if (PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY.containsKey(clazz)) {
      throw new IllegalStateException("cannot resolve empty byte array of type " + clazz);
    }
    return PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY.get(clazz);
  }

  static List<Field> getNonStaticFields(Field[] fields) {
    List<Field> fieldList = new ArrayList<>(fields.length);
    for (Field field : fields) {
      if (!Modifier.isStatic(field.getModifiers())) {
        fieldList.add(field);
      }
    }
    return fieldList;
  }

  /**
   * 序列化java对象成byte数组
   */
  public static byte[] encode(Object object) throws IOException {
    Class<?> clazz = object.getClass();
    final List<Field> nonStaticDeclaredFields;
    {
      Field[] declaredFields = clazz.getDeclaredFields();
      nonStaticDeclaredFields = getNonStaticFields(declaredFields);
    }
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (Field declaredField : nonStaticDeclaredFields) {
      byte[] bytes = encode(object, declaredField);
      byteArrayOutputStream.write(bytes);
    }
    return byteArrayOutputStream.toByteArray();
  }

  static byte[] encode(Object object, Field field) {
    field.trySetAccessible();
    final Object value;
    try {
      value = field.get(object);
    } catch (IllegalAccessException e) {
      throw new EncodeRuntimeException("cannot access field " + field + " in object " + object, e);
    }
    final Class<?> fieldType = field.getType();
    if (fieldType.isPrimitive()) {
      return encodePrimitive(fieldType, value);
    }
    if (fieldType.isArray()) {
      return encodeArray(fieldType, value);
    }

    throw new UnsupportedOperationException("field type " + fieldType + " in object " + object);
  }

  static byte[] encodePrimitive(Class<?> fieldType, Object value) {
    if (null == value) {
      return getEmptyByteArrayOf(fieldType);
    }
    if (Byte.TYPE.equals(fieldType)) {
      return new byte[] {(byte) value};
    }
    if (Integer.TYPE.equals(fieldType)) {
      return encodeInt((int) value);
    }
    if (Long.TYPE.equals(fieldType)) {
      return encodeLong((long) value);
    }
    if (Float.TYPE.equals(fieldType)) {
      return encodeFloat((float) value);
    }
    if (Double.TYPE.equals(fieldType)) {
      return encodeDouble((double) value);
    }
    throw new UnsupportedOperationException("primitive type " + fieldType + " value " + value);
  }

  static byte[] encodeShort(short value) {
    byte[] bytes = new byte[2];
    bytes[0] = (byte) (value & 0xFF);
    bytes[1] = (byte) (value >> 8);
    return bytes;
  }

  static byte[] encodeInt(int value) {
    byte[] lowPart = encodeShort((short) (value & 0xFFFF));
    byte[] highPart = encodeShort((short) (value >> 16));
    return ArrayUtils.concatByteArray(highPart, lowPart);
  }

  static byte[] encodeLong(long value) {
    byte[] lowPart = encodeInt((int) (value));
    byte[] highPart = encodeInt((int) (value >> 32));
    return ArrayUtils.concatByteArray(highPart, lowPart);
  }

  static byte[] encodeFloat(float value) {
    int rawIntBits = Float.floatToRawIntBits(value);
    return encodeInt(rawIntBits);
  }

  static byte[] encodeDouble(double value) {
    long rawLongBits = Double.doubleToRawLongBits(value);
    return encodeLong(rawLongBits);
  }

  static byte[] encodeArray(Class<?> fieldType, Object value) {
    Class<?> componentType = fieldType.getComponentType();
    if (Byte.TYPE.equals(componentType)) {
      return (byte[]) value;
    }
    throw new UnsupportedOperationException("array type " + fieldType + " value " + value);
  }

  public static <T> T decode(byte[] bytes, Class<T> clazz) {
    throw new UnsupportedOperationException();
  }

  static class EncodeRuntimeException extends RuntimeException {

    public EncodeRuntimeException(String message, Throwable e) {
      super(message, e);
    }
  }
}
