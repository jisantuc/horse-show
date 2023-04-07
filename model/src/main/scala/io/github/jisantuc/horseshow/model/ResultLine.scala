package io.github.jisantuc.horseshow.model

import cats.parse.Parser
import cats.parse.Parser0
import cats.parse.Rfc5234

case class ResultLine(
    round: Int,
    game: Game,
    competitor1: Competitor,
    competitor2: Competitor
)

object ResultLine {
  private val comma = Parser.char(',')
  val parser: Parser[ResultLine] = for {
    _           <- Rfc5234.digit.rep <* comma
    game        <- Game.parser <* comma
    round       <- Rfc5234.digit.rep.map(_.toList.mkString("").toInt) <* comma
    competitor1 <- Competitor.parser <* comma
    competitor2 <- Competitor.parser
  } yield ResultLine(round, game, competitor1, competitor2)
}
