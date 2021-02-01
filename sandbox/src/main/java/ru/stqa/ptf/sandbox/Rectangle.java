package ru.stqa.ptf.sandbox;

public class Rectangle {
  // атрибуты - длина двух сторон. объявляем переменные
  public double a;
  public double b;

  // коструктор с параметрами
  public Rectangle(double a, double b) {
    this.a = a;
    this.b = b;
  }

  // метод вычисления площади прямоугольника. не используется static, т.к. метод ассоциируется с объектом
  public double area() {
    // this - идентификатор доступности объекта
    return this.a * this.b;
  }
}
