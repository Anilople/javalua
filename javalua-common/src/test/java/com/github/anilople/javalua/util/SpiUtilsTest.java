package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.util.spi.MultipleImplInterface;
import com.github.anilople.javalua.util.spi.NoImplInterface;
import com.github.anilople.javalua.util.spi.NormalInterface;
import com.github.anilople.javalua.util.spi.WithArgumentInterface;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class SpiUtilsTest {

  @Test
  void loadOneInterfaceImpl_no_impl() {
    assertThrows(
        IllegalStateException.class, () -> SpiUtils.loadOneInterfaceImpl(NoImplInterface.class));
  }

  @Test
  void loadOneInterfaceImpl_multiple_impl() {
    assertThrows(
        IllegalStateException.class,
        () -> SpiUtils.loadOneInterfaceImpl(MultipleImplInterface.class));
  }

  @Test
  void loadOneInterfaceImpl_1_impl() {
    NormalInterface normalInterface = SpiUtils.loadOneInterfaceImpl(NormalInterface.class);
    assertEquals(3, normalInterface.add(1, 2));
  }

  @Test
  void loadOneInterfaceImpl_with_argument() {
    WithArgumentInterface withArgumentInterface =
        SpiUtils.loadOneInterfaceImpl(
            WithArgumentInterface.class, int.class, WithArgumentInterface.class, 999, null);
    assertEquals(999, withArgumentInterface.getN());
  }
}
