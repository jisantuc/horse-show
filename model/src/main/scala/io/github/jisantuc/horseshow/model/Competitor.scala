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

  private val threePartNameParser = for {
    firstName <- alphaString <* space
    suffix    <- alphaString <* space
    lastName  <- alphaString
  } yield s"$firstName $lastName $suffix"

  private val twoPartNameParser = for {
    firstName <- alphaString <* space
    lastName  <- alphaString
  } yield s"$firstName $lastName"

  val parser = for {
    name   <- threePartNameParser.backtrack orElse twoPartNameParser
    _      <- Rfc5234.sp <* Parser.char('(')
    losses <- Numbers.digits.map(_.toInt) <* Parser.char(')')
  } yield Competitor(name, losses)
}
