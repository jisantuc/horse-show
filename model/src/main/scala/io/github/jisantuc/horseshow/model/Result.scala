package io.github.jisantuc.horseshow.model

import cats.parse.Parser
import cats.parse.Parser0
import cats.parse.Rfc5234
import io.circe.Decoder
import io.circe.Encoder

/** Like {@link Result}, but with a winner and loser */
case class Result(
    round: Int,
    game: Game,
    winner: Competitor,
    loser: Competitor
)

object Result {
  private val comma = Parser.char(',')
  val parser: Parser[Result] = for {
    _           <- Rfc5234.digit.rep <* comma
    game        <- Game.parser <* comma
    round       <- Rfc5234.digit.rep.map(_.toList.mkString("").toInt) <* comma
    competitor1 <- Competitor.parser <* comma
    competitor2 <- Competitor.parser
  } yield Result(round, game, competitor1, competitor2)

  implicit val encoderResult: Encoder[Result] = Encoder.forProduct4(
    "round",
    "game",
    "winner",
    "loser"
  )(result => (result.round, result.game, result.winner, result.loser))

  implicit val decoderResult: Decoder[Result] =
    Decoder.forProduct4("round", "game", "winner", "loser")(
      Result.apply
    )
}
