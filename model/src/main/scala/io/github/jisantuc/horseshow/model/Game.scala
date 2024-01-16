package io.github.jisantuc.horseshow.model

import cats.parse.Parser
import cats.parse.Rfc5234
import cats.syntax.functor._
import io.circe.Encoder
import io.circe.Decoder

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

  implicit val encoderGame: Encoder[Game] =
    Encoder.encodeString.contramap(_.toString())
  implicit val decoderGame: Decoder[Game] = Decoder[String].emap {
    case "NineBall"  => Right(NineBall)
    case "BankPool"  => Right(BankPool)
    case "OnePocket" => Right(OnePocket)
    case s           => Left(s"Not a valid game: $s")
  }

}
