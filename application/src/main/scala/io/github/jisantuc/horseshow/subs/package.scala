package io.github.jisantuc.horseshow

import cats.effect.IO
import io.github.jisantuc.horseshow.http.requestData
import io.github.jisantuc.horseshow.model.*
import tyrian.Sub

import scala.concurrent.duration.*

package object subs {

  def dataSubscription2(seconds: Int): Sub[IO, Msg] =
    Sub.every[IO](seconds.seconds, "refresh-data").map(_ => Msg.RefreshData)

}
