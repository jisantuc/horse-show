package io.github.jisantuc.horseshow.model

import cats.parse.Numbers
import cats.parse.Parser
import cats.parse.Rfc5234

final case class Competitor(
    name: String,
    losses: Int
)

object Competitor {
  val parser = for {
    firstName <- Rfc5234.alpha.rep.map(_.toList.mkString)
    _         <- Rfc5234.sp.rep
    lastName  <- Rfc5234.alpha.rep.map(_.toList.mkString)
    _         <- Rfc5234.sp <* Parser.char('(')
    losses    <- Numbers.digits.map(_.toInt) <* Parser.char(')')
  } yield Competitor(s"$firstName $lastName", losses)
}
