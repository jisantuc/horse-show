package io.github.jisantuc.horseshow

import cats.effect.IO
import io.github.jisantuc.horseshow.http.requestData
import io.github.jisantuc.horseshow.model.*
import tyrian.Sub

import scala.concurrent.duration.*

package object subs {

  def dataSubscription(seconds: Int) = Sub.make[IO, Msg](
    "tourney-results",
    fs2.Stream
      .awakeEvery[IO](seconds.seconds)
      .flatMap(_ => fs2.Stream.eval(requestData))
      .map(Msg.DataReceived(_))
  )

}
