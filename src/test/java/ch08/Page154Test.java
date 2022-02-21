package ch08;

import com.github.anilople.javalua.api.LuaVM;
import constant.ResourceContentConstants.ch08;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class Page154Test {

  @Test
  void call() {
    LuaVM.evalAndPrint(ch08.call);
  }

  /**
   * 操作数B和C分别为0的情况
   */
  @Test
  void callOperandZero() {
    LuaVM.evalAndPrint(ch08.callOperandZero);
  }
}
