package io.github.jisantuc.horseshow

import cats.parse.Parser
import cats.syntax.either._
import cats.syntax.show._
import cats.Show

class ParseTest extends munit.FunSuite {
  def assertParseResultEquals[T](
      actual: Either[Parser.Error, T],
      expected: T
  ) = {
    assert(
      actual.isRight,
      actual.fold(_.show, _ => throw Exception("impossible"))
    )
    assertEquals(actual, Right(expected))
  }
}
