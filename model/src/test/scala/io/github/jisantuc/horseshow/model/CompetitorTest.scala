package io.github.jisantuc.horseshow.model

import cats.Show
import cats.parse.Parser
import cats.syntax.either._
import io.github.jisantuc.horseshow.ParseTest

class CompetitorTest extends ParseTest {
  test("parses successfully -- name with two spaces in the middle") {
    val parseResult = Competitor.parser.parseAll("A  Bb (0)")
    assertParseResultEquals(parseResult, Competitor("A Bb", 0, false))
  }

  test("parses successfully -- name with abbreviations") {
    val parseResult = Competitor.parser.parseAll("A.A. Milne (3)")
    assertParseResultEquals(parseResult, Competitor("A.A. Milne", 3, false))
  }

  test("parses successfully -- last name with hyphens") {
    val parseResult = Competitor.parser.parseAll("A B-B (4)")
    assertParseResultEquals(parseResult, Competitor("A B-B", 4, false))
  }

  test("parses successfully -- last name with two hyphens") {
    val parseResult = Competitor.parser.parseAll("A B-B-B (5)")
    assertParseResultEquals(parseResult, Competitor("A B-B-B", 5, false))
  }

  test("parses successfully -- name with two parts and normal spacing") {
    val parseResult = Competitor.parser.parseAll("A Bb (1)")
    assertParseResultEquals(parseResult, Competitor("A Bb", 1, false))
  }

  test("parses successfully -- name with jr in the middle") {
    val parseResult = Competitor.parser.parseAll("A Jr C (1)")
    assertParseResultEquals(parseResult, Competitor("A C Jr.", 1, false))
  }

  test("parses successfully -- name with jr at the end") {
    val parseResult = Competitor.parser.parseAll("A Bb Jr. (1)")
    assertParseResultEquals(parseResult, Competitor("A Bb Jr.", 1, false))
  }

  test("parses successfully -- name with sr in the middle") {
    val parseResult = Competitor.parser.parseAll("A Sr C (1)")
    assertParseResultEquals(parseResult, Competitor("A C Sr.", 1, false))
  }

  test("parses successfully -- name with middle initial") {
    val parseResult = Competitor.parser.parseAll("A B C (2)")
    assertParseResultEquals(parseResult, Competitor("A B C", 2, false))
  }

  test("parses successfully -- name with I") {
    val parseResult = Competitor.parser.parseAll("A Bb I (2)")
    assertParseResultEquals(parseResult, Competitor("A Bb I", 2, false))
  }

  test("parses successfully -- name with III") {
    val parseResult = Competitor.parser.parseAll("A Bb III (2)")
    assertParseResultEquals(parseResult, Competitor("A Bb III", 2, false))
  }

  test("parses successfully -- name with IIIIII") {
    val parseResult = Competitor.parser.parseAll("A Bb IIIIII (2)")
    assertParseResultEquals(parseResult, Competitor("A Bb IIIIII", 2, false))
  }

  test("parses successfully -- name with De X as last name") {
    val parseResult = Competitor.parser.parseAll("A De B (93)")
    assertParseResultEquals(parseResult, Competitor("A De B", 93, false))
  }

  test("parses successfully -- simple first middle last") {
    val parseResult = Competitor.parser.parseAll("Aa Bb Cc (3)")
    assertParseResultEquals(parseResult, Competitor("Aa Bb Cc", 3, false))
  }

  test("parses successfully -- result with buyback") {
    val parseResult = Competitor.parser.parseAll("Aa Bb (1B)")
    assertParseResultEquals(parseResult, Competitor("Aa Bb", 1, true))
  }

  test("parses successfully -- result with abbreviated middle name") {
    val parseResult = Competitor.parser.parseAll("Aa B. Cc (2B)")
    assertParseResultEquals(parseResult, Competitor("Aa B. Cc", 2, true))
  }

  test("parses successfully -- first middle last in combined parser") {
    val parseResult = Competitor.nameParser.parseAll("Aa Bb Cc")
    assertParseResultEquals(parseResult, "Aa Bb Cc")
  }

  test("parses successfully -- firstMiddleLastParser alone") {
    val parseResult = Competitor.firstMiddleLastParser.parseAll("Aa Bb Cc")
    assertParseResultEquals(parseResult, "Aa Bb Cc")
  }

}
