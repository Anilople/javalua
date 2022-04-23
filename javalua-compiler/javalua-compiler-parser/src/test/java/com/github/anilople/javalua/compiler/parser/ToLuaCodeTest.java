package com.github.anilople.javalua.compiler.parser;

import com.github.anilople.javalua.compiler.ast.Block;
import constant.ResourceContentConstants.ch02;
import constant.ResourceContentConstants.ch08;
import constant.ResourceContentConstants.ch11;
import constant.ResourceContentConstants.ch12;
import io.LuaTestResource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.LuaTestResourceUtils;

import static com.github.anilople.javalua.compiler.parser.LuaParserTest.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class ToLuaCodeTest {

  /**
   * raw lua code -> ast -> lua code
   */
  static String luaCodeToAstToLuaCode(final String rawLuaCode, final String luaFilePath) {
    try {
      Block block = LuaParser.parse(rawLuaCode, luaFilePath);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      block.toLuaCode(new PrintStream(byteArrayOutputStream));
      return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    } catch (Throwable e) {
      throw new IllegalStateException("parse " + luaFilePath, e);
    }
  }

  static void oneEquals(final String rawLuaCode, final String luaFilePath) {
    // 1. raw lua code -> ast -> lua code 1
    final String luaCode1 = luaCodeToAstToLuaCode(rawLuaCode, luaFilePath);
    // 2. lua code 1 -> ast -> lua code 2
    final String luaCode2 = luaCodeToAstToLuaCode(luaCode1, luaFilePath);
    // lua code 1 equals lua code 2
    assertEquals(luaCode1, luaCode2);
  }

  @Test
  void helloWorld() {
    Block block = parse(ch02.hello_world.getLuaCode());
    block.toLuaCode(System.out);
  }

  @Test
  void ch12_page_234_test() {
    Block block = parse(ch12.page_234_test.getLuaCode());
    block.toLuaCode(System.out);
  }

  @Test
  void ch08_test() {
    final String luaCode1 = luaCodeToAstToLuaCode(ch08.test.getLuaCode(), ch08.test.getLuaFilePath());
    System.out.println(luaCode1);
    final String luaCode2 = luaCodeToAstToLuaCode(luaCode1, ch08.test.getLuaFilePath());
    assertEquals(luaCode1, luaCode2);
  }

  @Test
  void ch11_TAILCALL_case1() {
    final String luaCode1 = luaCodeToAstToLuaCode(ch11.TAILCALL_case1.getLuaCode(), ch11.TAILCALL_case1.getLuaFilePath());
    System.out.println(luaCode1);
    final String luaCode2 = luaCodeToAstToLuaCode(luaCode1, ch11.TAILCALL_case1.getLuaFilePath());
    assertEquals(luaCode1, luaCode2);
  }

  @Test
  void allEquals() throws IOException {
    List<LuaTestResource> luaTestResources = LuaTestResourceUtils.getLuaTestResources();
    System.out.println("luaTestResources size " + luaTestResources.size());
    for (LuaTestResource luaTestResource : luaTestResources) {
      final String rawLuaCode = luaTestResource.getLuaCode();
      final String luaFilePath = luaTestResource.getLuaFilePath();
      oneEquals(rawLuaCode, luaFilePath);
    }
  }
}
