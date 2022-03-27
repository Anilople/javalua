package ch13;

import constant.ResourceContentConstants.ch13;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import util.LuaVMUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author wxq
 */
class Ch13Test {

  @Test
  void page_241_test() {
    String stdout = LuaVMUtils.run(ch13.page_241_test);
    System.out.println(stdout);
    List<String> lines = stdout.lines().collect(Collectors.toList());
    // 3行
    assertEquals(3, lines.size());
    // 第1行
    assertEquals("true\t2.0", lines.get(0));
    // 第2行
    assertTrue(lines.get(1).startsWith("false"));
    // 第3行
    assertTrue(lines.get(2).startsWith("false"));
  }
  
}
