package io.github.jisantuc.horseshow

import cats.effect.IO
import cats.syntax.all.*
import fs2.Pure
import fs2.Stream
import fs2.text
import io.circe.fs2.decoder
import io.circe.parser._
import io.circe.{Error => CirceError}
import io.github.jisantuc.horseshow.model.*
import tyrian.Cmd
import tyrian.http.Decoder
import tyrian.http.Http
import tyrian.http.Request

import scala.jdk.CollectionConverters._

package object http {
  private val someLines: List[ResultLine] = List(
    ResultLine(
      1,
      Game.NineBall,
      Competitor("James", 0, false),
      Competitor("Shane van Boening", 1, true)
    ),
    ResultLine(
      1,
      Game.BankPool,
      Competitor("Michael", 1, true),
      Competitor("Naoyuki Oi", 0, false)
    ),
    ResultLine(
      1,
      Game.NineBall,
      Competitor("Anne", 0, false),
      Competitor("Fedor Gorst", 1, true)
    ),
    ResultLine(
      2,
      Game.NineBall,
      Competitor("James", 0, false),
      Competitor("Anne", 1, true)
    )
  )

  def requestData: IO[List[ResultLine]] =
    IO.pure(someLines)

  def requestDataCmd: Cmd[IO, Msg] = Http.send(
    Request.get("https://d3s4yxerd7vsbm.cloudfront.net/dcc-results.ndjson"),
    Decoder[Msg](
      response =>
        val parsed: Either[CirceError, List[ResultLine]] =
          response.body.linesIterator.toList.traverse { line =>
            decode[ResultLine](line)
          }
        parsed
          .fold(
            err => Msg.RefreshDataFailed,
            data => Msg.RefreshDataSucceeded(data)
          )
      ,
      err => Msg.RefreshDataFailed
    )
  )
}
