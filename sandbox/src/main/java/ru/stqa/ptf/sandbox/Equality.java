package ru.stqa.ptf.sandbox;

public class Equality {
  public static void main (String[] args) {
    String s1 = "firefox";
    String s2 = new String(s1);

    //для сравнения чисел
    System.out.println(s1 == s2);
    //для сравнения объектов
    System.out.println(s1.equals(s2));
  }
}
