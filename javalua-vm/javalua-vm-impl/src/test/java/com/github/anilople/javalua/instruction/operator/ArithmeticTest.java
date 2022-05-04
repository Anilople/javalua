package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArithmeticTest {

  @Test
  void addLuaInteger() {
    assertEquals(LuaInteger.newLuaInteger(2), Arithmetic.addLuaInteger(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.addLuaInteger(LuaInteger.newLuaInteger(0), LuaInteger.newLuaInteger(0)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.addLuaInteger(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(-1)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.addLuaInteger(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(-1)));
  }

  @Test
  void addLuaNumber() {
    assertEquals(LuaNumber.newLuaNumber(2D), Arithmetic.addLuaNumber(LuaNumber.newLuaNumber(1D), LuaNumber.newLuaNumber(1D)));
    assertEquals(LuaNumber.newLuaNumber(0D), Arithmetic.addLuaNumber(LuaNumber.newLuaNumber(0D), LuaNumber.newLuaNumber(0D)));
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
    assertEquals(LuaInteger.newLuaInteger(-2), Arithmetic.subLuaInteger(LuaInteger.newLuaInteger(2)));
    assertEquals(LuaInteger.newLuaInteger(2), Arithmetic.subLuaInteger(LuaInteger.newLuaInteger(-2)));
  }

  @Test
  void multiplyLuaInteger() {
    assertEquals(LuaInteger.newLuaInteger(1), Arithmetic.multiplyLuaInteger(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(4), Arithmetic.multiplyLuaInteger(LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(2)));
    assertEquals(LuaInteger.newLuaInteger(-4), Arithmetic.multiplyLuaInteger(LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(-2)));
  }

  @Test
  void multiplyLuaNumber() {
    assertEquals(LuaNumber.newLuaNumber(1D), Arithmetic.multiplyLuaNumber(LuaNumber.newLuaNumber(1D), LuaNumber.newLuaNumber(1D)));
    assertEquals(LuaNumber.newLuaNumber(4D), Arithmetic.multiplyLuaNumber(LuaNumber.newLuaNumber(2D), LuaNumber.newLuaNumber(2D)));
    assertEquals(LuaNumber.newLuaNumber(-4D), Arithmetic.multiplyLuaNumber(LuaNumber.newLuaNumber(2D), LuaNumber.newLuaNumber(-2D)));
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
        LuaInteger.newLuaInteger(1), Arithmetic.floorDivisionLuaInteger(LuaInteger.newLuaInteger(5), LuaInteger.newLuaInteger(3)));
    assertEquals(
        LuaInteger.newLuaInteger(-2), Arithmetic.floorDivisionLuaInteger(LuaInteger.newLuaInteger(-5), LuaInteger.newLuaInteger(3)));
  }

  @Test
  void floorDivisionLuaNumber() {
    assertEquals(
        LuaNumber.newLuaNumber(-2.0D), Arithmetic.floorDivisionLuaNumber(LuaNumber.newLuaNumber(5D), LuaNumber.newLuaNumber(-3D)));
    assertEquals(
        LuaNumber.newLuaNumber(1.0D),
        Arithmetic.floorDivisionLuaNumber(LuaNumber.newLuaNumber(-5.0D), LuaNumber.newLuaNumber(-3.0D)));
  }

  @Test
  void floorDivision() {
    assertEquals(LuaNumber.newLuaNumber(-2.0D), Arithmetic.floorDivision(LuaInteger.newLuaInteger(5), LuaNumber.newLuaNumber(-3D)));
    assertEquals(LuaNumber.newLuaNumber(1.0D), Arithmetic.floorDivision(LuaNumber.newLuaNumber(-5.0D), LuaInteger.newLuaInteger(-3)));
  }

  @Test
  void moduloLuaInteger() {
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(0), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(0), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(2)));

    assertEquals(LuaInteger.newLuaInteger(1), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(3), LuaInteger.newLuaInteger(2)));
    assertEquals(LuaInteger.newLuaInteger(7), Arithmetic.moduloLuaInteger(LuaInteger.newLuaInteger(15), LuaInteger.newLuaInteger(8)));

    // TODO
    System.out.println(-3 % 2);
    //    assertEquals(LuaValue.of(-1), Arithmetic.moduloLuaInteger(LuaValue.of(-3),
    // LuaValue.of(2)));

  }

  @Test
  void moduleLuaNumber() {}

  @Test
  void power() {
    assertEquals(LuaNumber.newLuaNumber(1.0D), Arithmetic.power(LuaString.newLuaString("1"), LuaString.newLuaString("100")));
    assertEquals(LuaNumber.newLuaNumber(4.0D), Arithmetic.power(LuaString.newLuaString("2"), LuaString.newLuaString("2")));
    assertEquals(LuaNumber.newLuaNumber(16.0D), Arithmetic.power(LuaString.newLuaString("2"), LuaInteger.newLuaInteger(4)));
  }
}
