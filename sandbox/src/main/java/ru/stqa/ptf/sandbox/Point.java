package ru.stqa.ptf.sandbox;

public class Point {

  // атрибут - кордината. объявляем переменные
  double x;
  double y;

  // конструктор с координатами точки
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // метод вычисления расстояния до t2
  public double distance(Point t2) {
    // this.x - текущая точка с координатой х, t2.х - другая точка с координатой х
    return Math.sqrt((Math.pow(t2.x - this.x, 2)) + (Math.pow(t2.y - this.y, 2)));
  }
}