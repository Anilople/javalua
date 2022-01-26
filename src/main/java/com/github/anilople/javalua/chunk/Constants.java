package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.io.EncodeOutputStream;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class Constants implements Encodable, Decodable {

  Constant[] constants = new Constant[0];

  @Override
  public byte[] encode() {
    EncodeOutputStream outputStream = new EncodeOutputStream();
    outputStream.writeInt(this.constants.length);
    byte[] bytes = Encodable.encode(this.constants);
    outputStream.writeBytes(bytes);
    return outputStream.toByteArray();
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    int length = inputStream.readInt();
    this.constants = new Constant[length];
    Decodable.decode(Constant.class, this.constants, inputStream);
  }

  interface Tag {

    /**
     * lua nil 不存储
     */
    byte NIL = 0x00;
    /**
     * lua boolean 字节（0,1）
     */
    byte BOOLEAN = 0x01;
    /**
     * lua number Lua浮点数
     */
    byte NUMBER = 0x03;
    /**
     * lua integer Lua整数
     */
    byte INTEGER = 0x13;
    /**
     * lua string Lua短字符串
     */
    byte SHORT_STRING = 0x04;
    /**
     * lua string Lua长字符串
     */
    byte LONG_STRING = 0x14;
  }
}
