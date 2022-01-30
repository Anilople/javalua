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
  LuaString[] upvalueNames = new LuaString[0];

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    {
      int length = inputStream.readInt();
      this.lineInfo = new int[length];
      inputStream.readNIntegers(length);
    }

    {
      int length = inputStream.readInt();
      this.locVars = new LocVar[length];
      Decodable.decode(LocVar.class, this.locVars, inputStream);
    }

    {
      int length = inputStream.readInt();
      this.upvalueNames = new LuaString[length];
      Decodable.decode(LuaString.class, this.upvalueNames, inputStream);
    }
  }

  @Override
  public byte[] encode() {
    EncodeOutputStream outputStream = new EncodeOutputStream();

    outputStream.writeInt(this.lineInfo.length);
    outputStream.writeIntegers(this.lineInfo);

    outputStream.writeInt(this.locVars.length);
    outputStream.writeBytes(Encodable.encode(this.locVars));

    outputStream.writeInt(this.upvalueNames.length);
    outputStream.writeBytes(Encodable.encode(this.upvalueNames));

    return outputStream.toByteArray();
  }
}
