package io.github.jisantuc.horseshow.model

import cats.parse.Numbers
import cats.parse.Parser
import cats.parse.Rfc5234
import cats.syntax.applicative._

final case class Competitor(
    name: String,
    losses: Int
)

object Competitor {
  private val alphaString = Rfc5234.alpha.rep.map(_.toList.mkString)
  private val space       = Rfc5234.sp.rep

  private val abbreviatedFirstNameParser = (for {
    initial <- Rfc5234.alpha
    _       <- Parser.char('.')
  } yield s"$initial.").rep.map(_.toList.mkString)

  private val firstNameParser =
    abbreviatedFirstNameParser.backtrack orElse alphaString

  private val lastNameParser: Parser[String] = for {
    component1 <- alphaString
    component2 <- (Parser.char('-') *> lastNameParser).?
  } yield component2.fold(component1)(c2 => s"$component1-$c2")

  private val jrParser = (Parser.string("Jr") ~ Parser.char('.').?).as("Jr.")

  private val numberSuffixParser = Parser.char('I').as('I').rep.string

  private val suffixParser =
    jrParser.backtrack orElse numberSuffixParser.backtrack

  private val nameParser = for {
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

  val parser = for {
    name   <- nameParser
    _      <- space <* Parser.char('(')
    losses <- Numbers.digits.map(_.toInt) <* Parser.char(')')
  } yield Competitor(name, losses)
}
