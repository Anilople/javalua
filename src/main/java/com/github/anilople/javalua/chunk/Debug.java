package com.github.anilople.javalua.chunk;

import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class Debug implements Encodable, Decodable {

  /**
   * 行号表
   */
  int[] lineInfo = new int[0];
  /**
   * 局部变量表
   */
  LocVar[] locVars = new LocVar[0];
  /**
   * Upvalue名列表
   */
  String[] upvalueNames = new String[0];

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {}

  @Override
  public byte[] encode() {
    return new byte[0];
  }
}
