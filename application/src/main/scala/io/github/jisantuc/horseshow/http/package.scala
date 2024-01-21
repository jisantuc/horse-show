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
  def requestDataCmd: Cmd[IO, Msg] = Http.send(
    Request.get("https://d3s4yxerd7vsbm.cloudfront.net/dcc-results.ndjson"),
    Decoder[Msg](
      response =>
        val parsed: Either[CirceError, List[Result]] =
          response.body.linesIterator.toList.traverse { line =>
            decode[Result](line)
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
