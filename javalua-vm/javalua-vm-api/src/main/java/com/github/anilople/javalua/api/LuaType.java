package com.github.anilople.javalua.api;

import lombok.Getter;

@Getter
public enum LuaType {
  LUA_TNONE("none", -1),
  LUA_TNIL("nil", 0),
  LUA_TBOOLEAN("boolean", 1),
  LUA_TLIGHTUSERDATA("lightuserdata", 2),
  LUA_TNUMBER("number", 3),
  LUA_TSTRING("string", 4),
  LUA_TTABLE("table", 5),
  LUA_TFUNCTION("function", 6),
  LUA_TUSERDATA("userdata", 7),
  LUA_TTHREAD("thread", 8),
  LUA_NUMTAGS("umtags", 9),
  ;

  private final String typeName;
  private final int value;

  LuaType(String typeName, int value) {
    this.typeName = typeName;
    this.value = value;
  }

  public static LuaType decode(int value) {
    switch (value) {
      case -1:
        return LUA_TNONE;
      case 0:
        return LUA_TNIL;
      case 1:
        return LUA_TBOOLEAN;
      case 2:
        return LUA_TLIGHTUSERDATA;
      case 3:
        return LUA_TNUMBER;
      case 4:
        return LUA_TSTRING;
      case 5:
        return LUA_TTABLE;
      case 6:
        return LUA_TFUNCTION;
      case 7:
        return LUA_TUSERDATA;
      case 8:
        return LUA_TTHREAD;
      case 9:
        return LUA_NUMTAGS;
      default:
        throw new IllegalArgumentException("unknown value " + value);
    }
  }

  public static int encode(LuaType luaType) {
    switch (luaType) {
      case LUA_TNONE:
        return -1;
      case LUA_TNIL:
        return 0;
      case LUA_TBOOLEAN:
        return 1;
      case LUA_TLIGHTUSERDATA:
        return 2;
      case LUA_TNUMBER:
        return 3;
      case LUA_TSTRING:
        return 4;
      case LUA_TTABLE:
        return 5;
      case LUA_TFUNCTION:
        return 6;
      case LUA_TUSERDATA:
        return 7;
      case LUA_TTHREAD:
        return 8;
      case LUA_NUMTAGS:
        return 9;
      default:
        throw new IllegalArgumentException("unknown lua type " + luaType);
    }
  }
}
