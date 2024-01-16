package io.github.jisantuc.horseshow.model

import cats.parse.Parser
import cats.parse.Parser0
import cats.parse.Rfc5234
import io.circe.Decoder
import io.circe.Encoder

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

  implicit val encoderResultLine: Encoder[ResultLine] = Encoder.forProduct4(
    "round",
    "game",
    "competitor1",
    "competitor2"
  )(result =>
    (result.round, result.game, result.competitor1, result.competitor2)
  )

  implicit val decoderResultLine: Decoder[ResultLine] =
    Decoder.forProduct4("round", "game", "competitor1", "competitor2")(
      ResultLine.apply
    )
}
