package ru.stqa.ptf.sandbox;

public class Square {
  // атрибут - длина стороны. объявляем переменную
  public double l;

  // конструктор с параметрами
  public Square(double l) {
    this.l = l;
  }

  // метод вычисления площади квадрата. не используется static, т.к. метод ассоциируется с объектом
  public double area() {
    // this - идентификатор доступности объекта
    return this.l * this.l;
  }
}
