package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author wxq
 */
public class LuaResourceUtils {

  static final String LUAC = "luac53";
  static final String LUA_SUFFIX = ".lua";
  static final String OUT_SUFFIX = ".out";
  static final String BYTECODE_SUFFIX = ".bytecode";

  public static String resolveOutFilename(String luaFileName) {
    return luaFileName.replace(LUA_SUFFIX, OUT_SUFFIX);
  }

  public static String resolveBytecodeFilename(String luaFileName) {
    return luaFileName.replace(LUA_SUFFIX, BYTECODE_SUFFIX);
  }

  static String removeLuaSuffix(String luaFileName) {
    if (!luaFileName.endsWith(LUA_SUFFIX)) {
      throw new IllegalArgumentException(luaFileName + " doesn't end with " + LUA_SUFFIX);
    }
    return luaFileName.substring(0, luaFileName.length() - LUA_SUFFIX.length());
  }

  static void forEachLuaFile(Path directory, BiConsumer<File, String> consumer) throws IOException {
    List<Path> luaFiles =
        Files.walk(directory)
            .filter(path -> !Files.isDirectory(path))
            .filter(path -> path.getFileName().toString().endsWith(LUA_SUFFIX))
            .collect(Collectors.toList());
    for (Path luaFile : luaFiles) {
      final String luaFileName = luaFile.getFileName().toString();
      final File workingDirectory = luaFile.getParent().toFile();
      consumer.accept(workingDirectory, luaFileName);
    }
  }

  static void compileLua(File workingDirectory, String luaFileName) {
    final String outFileName = resolveOutFilename(luaFileName);
    ProcessBuilder processBuilder = new ProcessBuilder(LUAC, "-o", outFileName, luaFileName);
    // change working directory
    processBuilder.directory(workingDirectory);
    run(processBuilder);
  }

  static void decompileToBytecode(File workingDirectory, String luaFileName) {
    final String bytecodeFilename = resolveBytecodeFilename(luaFileName);
    ProcessBuilder processBuilder = new ProcessBuilder(LUAC, "-l", "-l", luaFileName);
    // change working directory
    processBuilder.directory(workingDirectory);
    // redirect output to a file
    processBuilder.redirectOutput(new File(workingDirectory, bytecodeFilename));
    run(processBuilder);
  }

  /**
   * 运行命令
   */
  static void run(ProcessBuilder processBuilder) {
    System.out.println("working directory " + processBuilder.directory().getAbsolutePath());
    System.out.println(String.join(" ", processBuilder.command()));
    System.out.println();
    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e) {
      throw new IllegalStateException("启动脚本失败", e);
    }
    final int exitCode;
    try {
      exitCode = process.waitFor();
    } catch (InterruptedException e) {
      throw new IllegalStateException("waitFor失败", e);
    }
    assert exitCode == 0;
    try (InputStream inputStream = process.getInputStream()) {
      byte[] bytes = inputStream.readAllBytes();
      System.out.println(new String(bytes));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  static void generateJavaCode(Path directory, String chapter) throws IOException {
    List<String> luaFileNames = new ArrayList<>();
    forEachLuaFile(
        directory,
        (workingDirectory, luaFileName) -> {
          if (workingDirectory.getAbsolutePath().contains(chapter)) {
            luaFileNames.add(luaFileName);
          }
        });

    if (luaFileNames.isEmpty()) {
      return;
    }

    StringBuilder stringBuilder = new StringBuilder();
    // interface ch08 {
    stringBuilder.append("interface ").append(chapter).append(" {").append(System.lineSeparator());
    for (String luaFileName : luaFileNames) {
      String variableName = removeLuaSuffix(luaFileName);
      String resourcePath = String.join("/", chapter, luaFileName);
      // byte[] closure = ResourceReadUtils.readBytes("ch08/closure.luac53.out");
      // LuaResource hello_world = new LuaResource("ch02/hello_world.lua");
      stringBuilder
          .append("\tLuaResource ")
          .append(variableName)
          .append(" = ")
          .append("new LuaResource(\"")
          .append(resourcePath)
          .append("\");")
          .append(System.lineSeparator());
    }
    // }
    stringBuilder.append("}");
    System.out.println(stringBuilder);
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Path directory = Paths.get("src", "test", "resources");
    System.out.println("directory " + directory.toAbsolutePath());
    forEachLuaFile(
        directory,
        (workingDirectory, luaFileName) -> {
          compileLua(workingDirectory, luaFileName);
          decompileToBytecode(workingDirectory, luaFileName);
        });

    for (int i = 0; i < 10; i++) {
      String chapter = "ch0" + i;
      generateJavaCode(directory, chapter);
    }

    for (int i = 10; i < 30; i++) {
      String chapter = "ch" + i;
      generateJavaCode(directory, chapter);
    }
  }
}
