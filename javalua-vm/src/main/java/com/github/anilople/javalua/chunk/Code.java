package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.io.EncodeOutputStream;
import com.github.anilople.javalua.util.ArrayUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wxq
 */
@ToString
@EqualsAndHashCode
public class Code implements Encodable, Decodable {
  private int[] code = new int[0];

  @Override
  public void decode(DecodeInputStream inputStream) {
    int length = inputStream.readInt();
    // 注意 code 是以 int 为单位的
    this.code = inputStream.readNIntegers(length);
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

  public Instruction[] getInstructions() {
    final int length = this.code.length;
    Instruction[] instructions = new Instruction[this.code.length];
    for (int i = 0; i < length; i++) {
      int codeValue = this.code[i];
      Instruction instruction = Instruction.of(codeValue);
      instructions[i] = instruction;
    }
    return instructions;
  }
}
