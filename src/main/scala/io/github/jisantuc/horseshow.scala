package io.github.jisantuc

import cats.effect.IO
import tyrian.Html.*
import tyrian.*

import scala.scalajs.js.annotation.*

@JSExportTopLevel("TyrianApp")
object horseshow extends TyrianApp[Msg, Model]:

  // TODO -- add pretend data
  val someLines: List[ResultLine] = List(
  )

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (Model(Filters(None, None, None), someLines), Cmd.None)

  // TODO -- add buttons that emit filter messages
  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.Increment => (model + 1, Cmd.None)
    case Msg.Decrement => (model - 1, Cmd.None)

  def view(model: Model): Html[Msg] =
    div(
      button(onClick(Msg.Decrement))("-"),
      div(model.toString),
      button(onClick(Msg.Increment))("+")
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None

// TODO -- re-model for filter changes and remote data
enum Msg:
  case Increment, Decrement
