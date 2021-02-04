package ru.stqa.ptf.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNG {
  @Test
  public void Point() {
    Point p1 = new Point(0, 1);
    Point p2 = new Point(1, 10);

    // выводим на экран полученное значение
    System.out.println("Расстояние между точками = " + p1.distance(p2));
    // сравниваем полученное значение с ожидаемым
    Assert.assertEquals(p1.distance(p2), 9.055385138137417);

  }
}
