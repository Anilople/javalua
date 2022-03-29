package com.github.anilople.javalua.instruction.operator;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

class ArithmeticTest {

  @Test
  void addLuaInteger() {
    assertEquals(LuaValue.of(2), Arithmetic.addLuaInteger(LuaValue.of(1), LuaValue.of(1)));
    assertEquals(LuaValue.of(0), Arithmetic.addLuaInteger(LuaValue.of(0), LuaValue.of(0)));
    assertEquals(LuaValue.of(0), Arithmetic.addLuaInteger(LuaValue.of(1), LuaValue.of(-1)));
    assertEquals(LuaValue.of(0), Arithmetic.addLuaInteger(LuaValue.of(1), LuaValue.of(-1)));
  }

  @Test
  void addLuaNumber() {
    assertEquals(LuaValue.of(2D), Arithmetic.addLuaNumber(LuaValue.of(1D), LuaValue.of(1D)));
    assertEquals(LuaValue.of(0D), Arithmetic.addLuaNumber(LuaValue.of(0D), LuaValue.of(0D)));
  }

  @Test
  void add() {}

  @Test
  void subLuaInteger() {}

  @Test
  void subLuaNumber() {}

  @Test
  void sub() {}

  @Test
  void testSubLuaInteger() {
    assertEquals(LuaValue.of(-2), Arithmetic.subLuaInteger(LuaValue.of(2)));
    assertEquals(LuaValue.of(2), Arithmetic.subLuaInteger(LuaValue.of(-2)));
  }

  @Test
  void multiplyLuaInteger() {
    assertEquals(LuaValue.of(1), Arithmetic.multiplyLuaInteger(LuaValue.of(1), LuaValue.of(1)));
    assertEquals(LuaValue.of(4), Arithmetic.multiplyLuaInteger(LuaValue.of(2), LuaValue.of(2)));
    assertEquals(LuaValue.of(-4), Arithmetic.multiplyLuaInteger(LuaValue.of(2), LuaValue.of(-2)));
  }

  @Test
  void multiplyLuaNumber() {
    assertEquals(LuaValue.of(1D), Arithmetic.multiplyLuaNumber(LuaValue.of(1D), LuaValue.of(1D)));
    assertEquals(LuaValue.of(4D), Arithmetic.multiplyLuaNumber(LuaValue.of(2D), LuaValue.of(2D)));
    assertEquals(LuaValue.of(-4D), Arithmetic.multiplyLuaNumber(LuaValue.of(2D), LuaValue.of(-2D)));
  }

  @Test
  void multiply() {}

  @Test
  void divisionLuaNumber() {}

  @Test
  void division() {}

  @Test
  void floorDivisionLuaInteger() {
    assertEquals(
        LuaValue.of(1), Arithmetic.floorDivisionLuaInteger(LuaValue.of(5), LuaValue.of(3)));
    assertEquals(
        LuaValue.of(-2), Arithmetic.floorDivisionLuaInteger(LuaValue.of(-5), LuaValue.of(3)));
  }

  @Test
  void floorDivisionLuaNumber() {
    assertEquals(
        LuaValue.of(-2.0D), Arithmetic.floorDivisionLuaNumber(LuaValue.of(5D), LuaValue.of(-3D)));
    assertEquals(
        LuaValue.of(1.0D),
        Arithmetic.floorDivisionLuaNumber(LuaValue.of(-5.0D), LuaValue.of(-3.0D)));
  }

  @Test
  void floorDivision() {
    assertEquals(LuaValue.of(-2.0D), Arithmetic.floorDivision(LuaValue.of(5), LuaValue.of(-3D)));
    assertEquals(LuaValue.of(1.0D), Arithmetic.floorDivision(LuaValue.of(-5.0D), LuaValue.of(-3)));
  }

  @Test
  void moduloLuaInteger() {
    assertEquals(LuaValue.of(0), Arithmetic.moduloLuaInteger(LuaValue.of(0), LuaValue.of(1)));
    assertEquals(LuaValue.of(0), Arithmetic.moduloLuaInteger(LuaValue.of(1), LuaValue.of(1)));
    assertEquals(LuaValue.of(0), Arithmetic.moduloLuaInteger(LuaValue.of(2), LuaValue.of(1)));
    assertEquals(LuaValue.of(0), Arithmetic.moduloLuaInteger(LuaValue.of(2), LuaValue.of(2)));

    assertEquals(LuaValue.of(1), Arithmetic.moduloLuaInteger(LuaValue.of(3), LuaValue.of(2)));
    assertEquals(LuaValue.of(7), Arithmetic.moduloLuaInteger(LuaValue.of(15), LuaValue.of(8)));

    // TODO
    System.out.println(-3 % 2);
    //    assertEquals(LuaValue.of(-1), Arithmetic.moduloLuaInteger(LuaValue.of(-3),
    // LuaValue.of(2)));

  }

  @Test
  void moduleLuaNumber() {}

  @Test
  void power() {
    assertEquals(LuaValue.of(1.0D), Arithmetic.power(LuaValue.of("1"), LuaValue.of("100")));
    assertEquals(LuaValue.of(4.0D), Arithmetic.power(LuaValue.of("2"), LuaValue.of("2")));
    assertEquals(LuaValue.of(16.0D), Arithmetic.power(LuaValue.of("2"), LuaValue.of(4)));
  }
}
