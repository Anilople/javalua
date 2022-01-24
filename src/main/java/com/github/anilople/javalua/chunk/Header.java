package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */

import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_DATA;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_FORMAT;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_INT;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_NUM;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUAC_VERSION;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.LUA_SIGNATURE;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.SIZE_OF_INSTRUCTION;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.SIZE_OF_LUA_INTEGER;
import static com.github.anilople.javalua.chunk.BinaryChunkConstants.SIZE_OF_LUA_NUMBER;

import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import lombok.Data;

/**
 * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/ldump.c#L197">ldump.c#L197</a>
 * for header's dump
 */
@Data
public class Header implements Encodable, Decodable {

  public static final Header INSTANCE = new Header();
  public static final int SIZE = INSTANCE.encode().length;

  byte[] luaSignature = LUA_SIGNATURE;
  byte luacVersion = LUAC_VERSION;
  byte luacFormat = LUAC_FORMAT;
  byte[] luacData = LUAC_DATA;
  byte sizeOfInstruction = SIZE_OF_INSTRUCTION;
  byte sizeOfLuaInteger = SIZE_OF_LUA_INTEGER;
  byte sizeOfLuaNumber = SIZE_OF_LUA_NUMBER;
  long luacInt = LUAC_INT;
  double luacNum = LUAC_NUM;

  @Override
  public byte[] encode() {
    try {
      return ByteUtils.encode(this);
    } catch (IOException e) {
      throw new IllegalStateException("cannot dump " + this, e);
    }
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.luaSignature = inputStream.readNBytes(LUA_SIGNATURE.length);
    this.luacVersion = inputStream.readByte();
    this.luacFormat = inputStream.readByte();
    this.luacData = inputStream.readNBytes(LUAC_DATA.length);
    this.sizeOfInstruction = inputStream.readByte();
    this.sizeOfLuaInteger = inputStream.readByte();
    this.sizeOfLuaNumber = inputStream.readByte();
    this.luacInt = inputStream.readLong();
    this.luacNum = inputStream.readDouble();
  }
}
