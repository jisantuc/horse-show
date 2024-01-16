package io.github.jisantuc.horseshow

import cats.Show
import cats.parse.Parser
import cats.syntax.either._
import cats.syntax.show._

class ParseTest extends munit.FunSuite {
  def assertParseResultEquals[T](
      actual: Either[Parser.Error, T],
      expected: T
  ) = {
    assert(
      actual.isRight,
      actual.fold(_.show, _ => "impossible")
    )
    assertEquals(actual, Right(expected))
  }
}
