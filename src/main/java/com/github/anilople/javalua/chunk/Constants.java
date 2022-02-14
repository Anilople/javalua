package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.io.EncodeOutputStream;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wxq
 */
@ToString
@EqualsAndHashCode
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
  public void decode(DecodeInputStream inputStream) {
    int length = inputStream.readInt();
    this.constants = new Constant[length];
    Decodable.decode(Constant.class, this.constants, inputStream);
  }

  public Constant getConstant(int index) {
    return this.constants[index];
  }

  public int size() {
    return this.constants.length;
  }

}
