package ch08;

import com.github.anilople.javalua.api.LuaVM;
import constant.ResourceContentConstants.ch08;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class Page154Test {

  @Test
  @Disabled("GETTABUP 未实现")
  void call() {
    LuaVM.evalAndPrint(ch08.call.getLuacOut());
  }

  /**
   * 操作数B和C分别为0的情况
   */
  @Test
  @Disabled("GETTABUP 未实现")
  void callOperandZero() {
    LuaVM.evalAndPrint(ch08.call_operand_zero.getLuacOut());
  }
}
