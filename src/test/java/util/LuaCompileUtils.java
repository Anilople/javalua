package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxq
 */
public class LuaCompileUtils {

  /**
   * 把文件夹下的所有lua代码，都进行编译
   *
   * @param luac lua编译器的命令
   * @param directory 文件夹
   */
  static void compile(String luac, Path directory) throws IOException, InterruptedException {
    List<Path> luaFiles =
        Files.walk(directory)
            .filter(path -> !Files.isDirectory(path))
            .filter(path -> path.getFileName().toString().endsWith(".lua"))
            .collect(Collectors.toList());
    final String luacOutSuffix = "." + luac + ".out";
    for (Path luaFile : luaFiles) {
      String luaFileName = luaFile.getFileName().toString();
      String outFileName = luaFileName.replace(".lua", luacOutSuffix);
      ProcessBuilder processBuilder = new ProcessBuilder(luac, "-o", outFileName, luaFileName);
      // change working directory
      File workingDirectory = luaFile.getParent().toFile();
      processBuilder.directory(workingDirectory);
      System.out.println("working directory " + workingDirectory.getAbsolutePath());
      System.out.println(String.join(" ", processBuilder.command()));
      System.out.println();
      Process process = processBuilder.start();
      int exitCode = process.waitFor();
      assert exitCode == 0;
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Path directory = Paths.get("src", "test", "resources");
    System.out.println("directory " + directory.toAbsolutePath());
    compile("luac53", directory);
  }
}
