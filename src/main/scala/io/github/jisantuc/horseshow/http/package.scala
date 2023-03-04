package io.github.jisantuc.horseshow

import cats.effect.IO
import io.github.jisantuc.horseshow.model.*

package object http {
  private val someLines: List[ResultLine] = List(
    ResultLine(
      1,
      Game.NineBall,
      Competitor("James", 0),
      Competitor("Shane van Boening", 1)
    ),
    ResultLine(
      1,
      Game.BankPool,
      Competitor("Michael", 1),
      Competitor("Naoyuki Oi", 0)
    ),
    ResultLine(
      1,
      Game.NineBall,
      Competitor("Anne", 0),
      Competitor("Fedor Gorst", 1)
    ),
    ResultLine(
      2,
      Game.NineBall,
      Competitor("James", 0),
      Competitor("Anne", 1)
    )
  )

  def requestData: IO[List[ResultLine]] =
    IO.pure(someLines)

}
