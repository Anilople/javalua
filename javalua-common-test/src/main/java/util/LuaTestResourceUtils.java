package util;

import constant.ResourceContentConstants;
import io.LuaTestResource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author wxq
 */
public class LuaTestResourceUtils {

  static final String LUAC = "luac53";
  static final String LUA_SUFFIX = ".lua";
  static final String OUT_SUFFIX = ".out";
  static final String BYTECODE_SUFFIX = ".bytecode";

  public static void main(String[] args) throws IOException, InterruptedException {
    List<Path> luaFiles = resolveLuaFilePaths();
    compileAllLuaCode(luaFiles);
    generateResourceContentConstants(luaFiles);
  }

  public static String resolveOutFilename(String luaFileName) {
    return luaFileName.replace(LUA_SUFFIX, OUT_SUFFIX);
  }

  public static Path resolveOutFilePath(Path luaFile) {
    String luaFileName = luaFile.getFileName().toString();
    Path directory = luaFile.getParent();
    String outFileName = resolveOutFilename(luaFileName);
    return directory.resolve(outFileName);
  }

  static String resolveBytecodeFilename(String luaFileName) {
    return luaFileName.replace(LUA_SUFFIX, BYTECODE_SUFFIX);
  }

  static String removeLuaSuffix(String luaFileName) {
    if (!luaFileName.endsWith(LUA_SUFFIX)) {
      throw new IllegalArgumentException(luaFileName + " doesn't end with " + LUA_SUFFIX);
    }
    return luaFileName.substring(0, luaFileName.length() - LUA_SUFFIX.length());
  }

  static void forEachLuaFile(Collection<Path> luaFiles, BiConsumer<File, String> consumer) {
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
    String stringForStdout =
        "working directory "
            + processBuilder.directory().getAbsolutePath()
            + "\n"
            + String.join(" ", processBuilder.command());
    System.out.println(stringForStdout);
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

  static void generateJavaCode(String chapter, List<String> luaFileNames) {
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

  static boolean isTestResource(Path path) {
    String absPath = path.toAbsolutePath().toString();
    if (!absPath.contains("src")) {
      return false;
    }
    if (!absPath.contains("test")) {
      return false;
    }
    if (!absPath.contains("resources")) {
      return false;
    }
    return true;
  }

  static List<Path> findAllLuaFilesUnderTestResources(Path directory) throws IOException {
    List<Path> luaFiles =
        Files.walk(directory)
            .filter(path -> !Files.isDirectory(path))
            .filter(path -> path.getFileName().toString().endsWith(LUA_SUFFIX))
            .collect(Collectors.toList());
    return luaFiles.stream()
        .filter(LuaTestResourceUtils::isTestResource)
        .collect(Collectors.toUnmodifiableList());
  }

  static Path resolveJavaLuaDirectory() {
    final String folderName = "javalua";
    for (Path currentDirectory = Paths.get(".").toAbsolutePath();
        null != currentDirectory && !currentDirectory.equals(currentDirectory.getParent());
        currentDirectory = currentDirectory.getParent()) {
      if (folderName.equals(currentDirectory.getFileName().toString())) {
        return currentDirectory;
      }
    }
    throw new IllegalStateException("cannot find directory " + folderName);
  }

  static List<Path> resolveLuaFilePaths() throws IOException {
    Path currentDirectory = resolveJavaLuaDirectory();
    System.out.println("currentDirectory " + currentDirectory.toAbsolutePath());
    return findAllLuaFilesUnderTestResources(currentDirectory);
  }

  /**
   * 生成 {@link ResourceContentConstants} 里的内容
   */
  static void generateResourceContentConstants(List<Path> luaFiles) {
    for (int i = 0; i < 30; i++) {
      final String chapter;
      if (i < 10) {
        chapter = "ch0" + i;
      } else {
        chapter = "ch" + i;
      }
      List<String> luaFileNames = new ArrayList<>();
      for (Path path : luaFiles) {
        if (path.toAbsolutePath().toString().contains(chapter)) {
          luaFileNames.add(path.getFileName().toString());
        }
      }
      generateJavaCode(chapter, luaFileNames);
    }
  }

  /**
   * 编译所有lua代码
   */
  static void compileAllLuaCode(List<Path> luaFiles) {
    System.out.println("luaFiles " + luaFiles);
    forEachLuaFile(
        luaFiles,
        (workingDirectory, luaFileName) -> {
          compileLua(workingDirectory, luaFileName);
          decompileToBytecode(workingDirectory, luaFileName);
        });
  }

  public static List<LuaTestResource> getLuaTestResources() throws IOException {
    List<Path> luaFiles = resolveLuaFilePaths();
    List<LuaTestResource> luaTestResources = new ArrayList<>();
    for (Path luaFile : luaFiles) {
      LuaTestResource luaTestResource = LuaTestResource.resolve(luaFile);
      luaTestResources.add(luaTestResource);
    }
    return luaTestResources;
  }
}
