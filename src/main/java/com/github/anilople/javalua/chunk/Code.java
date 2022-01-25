package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ArrayUtils;
import java.io.IOException;

/**
 * @author wxq
 */
public class Code implements Encodable, Decodable {
  private int[] code = new int[0];

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    int length = inputStream.readInt();
    byte[] bytes = inputStream.readNBytes(length);
  }

  @Override
  public byte[] encode() {
    // https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/ldump.c
    /*
     static void dumpCode (DumpState *D, const Proto *f) {
       dumpInt(D, f->sizecode);
       dumpVector(D, f->code, f->sizecode);
     }
    */
    EncodeOutputStream outputStream = new EncodeOutputStream();
    outputStream.writeInt(this.code.length);
    outputStream.writeBytes(ArrayUtils.toByteArray(this.code));
    return outputStream.toByteArray();
  }
}
