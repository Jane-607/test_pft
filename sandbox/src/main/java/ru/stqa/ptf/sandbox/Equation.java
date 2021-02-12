package ru.stqa.ptf.sandbox;

public class Equation {
  private double a;
  private double b;
  private double c;

  private int n;

  public Equation(double a, double b, double c) {

    this.a = a;
    this.b = b;
    this.c = c;

    double d = b * b - 4 * a * c;

    //неполная форма
    if (a == 0) {
      System.out.println("Это вырожденное уравнение");
    }

    //свернутая форма
    if (d > 0) { n = 2; }
    else if (d == 0) { n = 1; }
    else { n = 0; }

    //вложенная форма
    if (d > 0) { n = 2; }
    else { if (d == 0) { n = 1; }
    else { n = 0; }
    }
  }

  public int rootNunber() {
    return n;
  }
}

