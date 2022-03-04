package util;

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
    List<Path> luaFiles = Files.walk(directory)
        .filter(path -> !Files.isDirectory(path))
        .filter(path -> path.getFileName().toString().endsWith(".lua"))
        .collect(Collectors.toList());
    for (Path luaFile : luaFiles) {
      String outFileName = luaFile.getFileName().toString().replace(".lua", ".luac53.out");
      Path outFile = luaFile.getParent().resolve(outFileName);
      ProcessBuilder processBuilder = new ProcessBuilder(
          luac, "-o", outFile.toAbsolutePath().toAbsolutePath().toString(), luaFile.toAbsolutePath().toString()
      );
      System.out.println(String.join(" ", processBuilder.command()));
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
