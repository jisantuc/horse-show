package io.github.jisantuc.horseshow.model

import io.github.jisantuc.horseshow.ParseTest

class GameTest extends ParseTest {
  test("successful parse for nine ball") {
    val parseResult = Game.parser.parseAll("9 Ball")
    assertParseResultEquals(parseResult, Game.NineBall)
  }

  test("successful parse for one pocket") {
    val parseResult = Game.parser.parseAll("One Pocket")
    assertParseResultEquals(parseResult, Game.OnePocket)
  }
  test("successful parse for bank pool") {
    val parseResult = Game.parser.parseAll("Banks")
    assertParseResultEquals(parseResult, Game.BankPool)
  }
}
