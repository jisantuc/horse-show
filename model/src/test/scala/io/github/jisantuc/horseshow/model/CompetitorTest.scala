package io.github.jisantuc.horseshow.model

import cats.Show
import cats.parse.Parser
import cats.syntax.either._
import io.github.jisantuc.horseshow.ParseTest

class CompetitorTest extends ParseTest {
  test("parses successfully -- name with two spaces in the middle") {
    val parseResult = Competitor.parser.parseAll("A  Bb (0)")
    assertParseResultEquals(parseResult, Competitor("A Bb", 0))
  }

  test("parses successfully -- name with abbreviations") {
    val parseResult = Competitor.parser.parseAll("A.A. Milne (3)")
    assertParseResultEquals(parseResult, Competitor("A.A. Milne", 3))
  }

  test("parses successfully -- last name with hyphens") {
    val parseResult = Competitor.parser.parseAll("A B-B (4)")
    assertParseResultEquals(parseResult, Competitor("A B-B", 4))
  }

  test("parses successfully -- last name with two hyphens") {
    val parseResult = Competitor.parser.parseAll("A B-B-B (5)")
    assertParseResultEquals(parseResult, Competitor("A B-B-B", 5))
  }

  test("parses successfully -- name with two parts and normal spacing") {
    val parseResult = Competitor.parser.parseAll("A Bb (1)")
    assertParseResultEquals(parseResult, Competitor("A Bb", 1))
  }

  test("parses successfully -- name with three parts") {
    val parseResult = Competitor.parser.parseAll("A Jr C (1)")
    assertParseResultEquals(parseResult, Competitor("A C Jr.", 1))
  }

  test("parses successfully -- name with middle initial") {
    val parseResult = Competitor.parser.parseAll("A B C (2)")
    assertParseResultEquals(parseResult, Competitor("A B C", 2))
  }
}
