package com.nikita.popularmovies.presentation;


public final class DefaultParams {
  public static void fun() {
    new Foo(1);
    new Foo(2, 3);
    new Foo(2, 8, 3);


    getNumber();
    getNumber(5);
    getNumber(5, 8);
  }

  public static final class Foo {
    public final int first;
    public final int second;

    public Foo(int second, int first) {
      this.first = first;
      this.second = second;
    }

    public Foo(int both) {
      this(both, both);
    }

    public Foo(int second, int multiplier, int first) {
      this(second * multiplier, first * multiplier);
    }
  }

  public static int getNumber() {
    return 4;
  }

  public static int getNumber(int number) {
    return getNumber() + number;
  }

  public static int getNumber(int number, int multiplier) {
    return getNumber(number * multiplier);
  }
}
