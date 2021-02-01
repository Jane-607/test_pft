package ru.stqa.ptf.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    hello("world");
    hello("user");
    hello("Jane");


    //создаем объект типа квадрат со значением параметра
    Square s = new Square(5);
    // для объекта s вызывается метод area
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    //создаем объект типа прямоугольник со значением параметров
    Rectangle r = new Rectangle(4, 6);
    // для объекта r вызывается метод area
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

    //создаем объект типа точка - t1
    Point t1 = new Point(2, 5);
    //создаем объект типа точка - t2
    Point t2 = new Point(8, 10);
    // выводим координаты точек t1,t2
    System.out.println("Координаты точки t1 = " + t1.x + "; " + t1.y);
    System.out.println("Координаты точки t2 = " + t2.x + "; " + t2.y);
    // для объектов t1,t2 вызываем метод distance. он обращается к точке t1, меряет расстояние до t2.
    System.out.println("Расстояние между точками = " + t1.distance(t2));
  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }
}