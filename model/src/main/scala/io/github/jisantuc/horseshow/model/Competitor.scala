package io.github.jisantuc.horseshow.model

import cats.parse.Numbers
import cats.parse.Parser
import cats.parse.Rfc5234

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

  private val threePartNameParser = for {
    firstName <- firstNameParser <* space
    suffix    <- alphaString <* space
    lastName  <- lastNameParser
  } yield s"$firstName $lastName $suffix"

  private val twoPartNameParser = for {
    firstName <- firstNameParser <* space
    lastName  <- lastNameParser
  } yield s"$firstName $lastName"

  val parser = for {
    name   <- threePartNameParser.backtrack orElse twoPartNameParser
    _      <- space <* Parser.char('(')
    losses <- Numbers.digits.map(_.toInt) <* Parser.char(')')
  } yield Competitor(name, losses)
}
