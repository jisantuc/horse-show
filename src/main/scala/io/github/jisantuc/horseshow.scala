package io.github.jisantuc

import cats.effect.IO
import io.github.jisantuc.model.*
import tyrian.Html.*
import tyrian.*

import scala.scalajs.js.annotation.*

@JSExportTopLevel("TyrianApp")
object horseshow extends TyrianApp[Msg, Model]:

  // TODO -- add pretend data
  val someLines: List[ResultLine] = List(
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
    )
  )

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (Model(Filters(None, None, None), someLines, someLines), Cmd.None)

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.DataReceived(rows) => (model, Cmd.None)
    case Msg.ToggleGameFilter =>
      val oldFilters      = model.filters
      val newGameFilters  = oldFilters.game.fold(Some(Game.NineBall))(_ => None)
      val newFilters      = oldFilters.copy(game = newGameFilters)
      val newFilteredData = newFilters.filterRows(model.data)
      (
        model.copy(filters = newFilters, filteredData = newFilteredData),
        Cmd.None
      )

  def view(model: Model): Html[Msg] =
    div(
      div(
        input(
          _type := "checkbox",
          label := "9 Ball only",
          onClick(Msg.ToggleGameFilter)
        ),
        text("9 Ball only")
      ),
      div(model.filteredData.map(render.resultRowToHtml))
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None
