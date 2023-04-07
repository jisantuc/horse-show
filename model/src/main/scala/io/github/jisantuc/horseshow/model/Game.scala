package io.github.jisantuc.horseshow.model

import cats.parse.Parser
import cats.parse.Rfc5234
import cats.syntax.functor._

enum Game:
  case NineBall, BankPool, OnePocket

object Game {
  private val nineBallParser: Parser[Game] =
    Parser.string("9 Ball").map(_ => NineBall)
  private val bankPoolParser: Parser[Game] =
    Parser.string("Banks").map(_ => BankPool)
  private val onePocketParser: Parser[Game] =
    Parser.string("One Pocket").map(_ => OnePocket)
  val parser: Parser[Game] =
    nineBallParser.orElse(bankPoolParser).orElse(onePocketParser)

}
