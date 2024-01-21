package io.github.jisantuc.horseshow.model

import cats.parse.Numbers
import cats.parse.Parser
import cats.parse.Rfc5234
import cats.syntax.applicative._
import io.circe.Decoder
import io.circe.Encoder

final case class Competitor(
    name: String,
    losses: Int,
    buyBack: Boolean
)

object Competitor {

  implicit val encoderCompetitor: Encoder[Competitor] =
    Encoder.forProduct3("name", "losses", "buyBack")(c =>
      (c.name, c.losses, c.buyBack)
    )
  implicit val decoderCompetitor: Decoder[Competitor] =
    Decoder.forProduct3("name", "losses", "buyBack")(Competitor.apply)

  private val anyNameIshChar =
    Parser.char('-') orElse Parser.char('.') orElse Parser.char('\'')
  private val alphaString = (Rfc5234.alpha orElse anyNameIshChar).rep.string
  private val space       = Rfc5234.sp.rep

  private val abbreviatedNameParser = (for {
    initial <- Rfc5234.alpha
    _       <- Parser.char('.')
  } yield s"$initial.").rep.map(_.toList.mkString)

  private val firstNameParser =
    abbreviatedNameParser.backtrack orElse alphaString

  private val lastNameParser: Parser[String] = for {
    prefix     <- Parser.string("De ").as("De ").?.backtrack.with1
    component1 <- alphaString
    component2 <- (Parser.char('-') *> lastNameParser).?
  } yield s"${prefix
    .getOrElse("")}${component2.fold(component1)(c2 => s"$component1-$c2")}"

  private val srParser =
    ((Parser.string("Sr") ~ Parser.char('.').?) | Parser.string("Senior"))
      .as("Sr.")

  private val jrParser = (Parser.string("Jr") ~ Parser.char('.').?).as("Jr.")

  private val numberSuffixParser = Parser.char('I').as('I').rep.string

  private val suffixParser =
    jrParser.backtrack orElse srParser.backtrack orElse numberSuffixParser.backtrack

  // different from the complex in that it ignores suffixes that might be mixed throughout
  private[model] val firstMiddleLastParser = for {
    firstName <- firstNameParser <* space
    middle <-
      firstNameParser between (!suffixParser, space) // middle names are like first names often?
    lastName <-
      !suffixParser *> lastNameParser <* (space *> !Rfc5234.alpha).backtrack
  } yield s"$firstName $middle $lastName"

  val complexNameParser = for {
    firstName <- firstNameParser <* space
    suffix1   <- (suffixParser <* space).backtrack.?
    middleInitial <- suffix1.fold((Rfc5234.char <* space).backtrack.?)(_ =>
      Parser.pure(Option.empty[Char])
    )
    lastName <- lastNameParser
    suffix2  <- (space *> suffixParser).backtrack.?
  } yield {
    val suffix = suffix1 orElse suffix2
    val plain  = s"$firstName $lastName"
    suffix.fold(
      middleInitial.fold(plain)(initial => s"$firstName $initial $lastName")
    )(suffix => s"$firstName $lastName $suffix")
  }

  private[model] val nameParser: Parser[String] =
    firstMiddleLastParser.backtrack | complexNameParser.backtrack

  val parser = for {
    name <- nameParser
    // the space might already have been consumed by the name parser
    _      <- space.? <* Parser.char('(')
    losses <- Numbers.digits.map(_.toInt)
    buyBack <- Parser.char('B').as(true).orElse(Parser.pure(false)) <* Parser
      .char(')')
  } yield Competitor(name, losses, buyBack)
}
