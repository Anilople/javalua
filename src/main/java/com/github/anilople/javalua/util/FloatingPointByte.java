package com.github.anilople.javalua.util;

/**
 * 为了解决9个比特无法表示超过512的数字，造成NEWTABLE的size无法足够大，Lua使用一种叫浮点字节（Floating Point Byte）的编码方式
 * <p/>
 * 仅用1个字节来表示浮点数，例如，对于 eeeeexxx，当 eeeee == 0时，表示整数xxx，否则表示 (1xxx) * 2^(eeeee - 1)
 *
 * @author wxq
 */
public class FloatingPointByte {

  /**
   * decode from Floating Point Byte
   *
   * @param eeeeexxx Floating Point Byte
   * @return int
   */
  public static int decode(int eeeeexxx) {
    var xxx = ByteUtils.getBits(eeeeexxx, 0, 3);
    var eeeee = ByteUtils.getBits(eeeeexxx, 3, 5);
    if (0 == eeeee) {
      return xxx;
    } else {
      // 1xxx
      var leftPart = 8 + xxx;
      // 2^(eeeee - 1)
      var rightPart = 1 << (eeeee - 1);
      // 相当于 (8 + xxx) << (eeeee - 1)
      return leftPart * rightPart;
    }
  }

  /**
   * encode to Floating Point Byte
   *
   * @param value int
   * @return Floating Point Byte
   */
  public static int encode(int value) {
    if (value < 8) {
      return value;
    }
    var highest1Position = ByteUtils.getHighest1Position(value);
    var eeeee = (highest1Position - 3) + 1;
    var xxx = (value >> (eeeee - 1)) - 8;
    return (eeeee << 3) | xxx;
  }
}
