package com.nikita.popularmovies.presentation

fun foo() {
  Foo(first = 1)
  Foo(2, 3)
  Foo(2, 8, 3)

  FooSimple()
  FooSimple(first = 3)
  FooSimple(second = 3)
  FooSimple(second = 3, first = 2)

  getNumber()
  getNumber(5)
  getNumber(5, 8)
}

data class FooSimple(val first: Int = 4, val second: Int = 5)

class Foo(first: Int, multiplier: Int = 1, second: Int = 0) {
  val first: Int = first * multiplier
  val second: Int = if (second == 0) this.first else second * multiplier
}

@JvmOverloads
fun getNumber(number: Int = 0, multiplier: Int = 1) = (4 + number) * multiplier