package io.github.jisantuc.horseshow.model

import cats.Show
import cats.parse.Parser
import cats.syntax.either._
import io.github.jisantuc.horseshow.ParseTest

class CompetitorTest extends ParseTest {
  test("parses successfully -- name with two spaces in the middle") {
    val parseResult = Competitor.parser.parseAll("A  B (0)")
    assertParseResultEquals(parseResult, Competitor("A B", 0))
  }

  test("parses successfully -- name with three parts") {
    val parseResult = Competitor.parser.parseAll("A b C (1)")
    assertParseResultEquals(parseResult, Competitor("A b C", 1))
  }
}
