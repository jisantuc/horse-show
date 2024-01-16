package io.github.jisantuc.horseshow

import cats.effect.IO
import io.github.jisantuc.horseshow.model.*

package object http {
  private val someLines: List[Result] = List(
    Result(
      1,
      Game.NineBall,
      Competitor("James", 0, false),
      Competitor("Shane van Boening", 1, true)
    ),
    Result(
      1,
      Game.BankPool,
      Competitor("Michael", 1, true),
      Competitor("Naoyuki Oi", 0, false)
    ),
    Result(
      1,
      Game.NineBall,
      Competitor("Anne", 0, false),
      Competitor("Fedor Gorst", 1, true)
    ),
    Result(
      2,
      Game.NineBall,
      Competitor("James", 0, false),
      Competitor("Anne", 1, true)
    )
  )

  def requestData: IO[List[Result]] =
    IO.pure(someLines)

}
