package io.github.jisantuc

import cats.effect.IO
import io.github.jisantuc.components.FilterBar
import io.github.jisantuc.components.GameFilter
import io.github.jisantuc.components.NameFilter
import io.github.jisantuc.components.RoundFilter
import io.github.jisantuc.model.*
import io.github.jisantuc.render.flex
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
    ),
    ResultLine(
      2,
      Game.NineBall,
      Competitor("James", 0),
      Competitor("Anne", 1)
    )
  )

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (
      Model(Display.On, Filters(None, None, None), someLines, someLines),
      Cmd.None
    )

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.DataReceived(rows) => (model, Cmd.None)
    case Msg.PickGame(g) =>
      val newFilters      = model.filters.copy(game = Some(g))
      val newFilteredData = newFilters.filterRows(model.data)
      (
        model.copy(filters = newFilters, filteredData = newFilteredData),
        Cmd.None
      )
    case Msg.ClearGameFilter =>
      (
        model.copy(
          filters = model.filters.copy(game = None),
          filteredData = model.data
        ),
        Cmd.None
      )
    case Msg.ToggleFilterDisplay =>
      (
        model.copy(filterBarStatus = model.filterBarStatus.toggle),
        Cmd.None
      )

  def view(model: Model): Html[Msg] =
    div(_class := "flex-column")(
      button(onClick(Msg.ToggleFilterDisplay))("Show filters"),
      FilterBar(
        model.filterBarStatus,
        GameFilter(model.filters.game),
        NameFilter(),
        RoundFilter()
      ).render,
      table(styles("border-collapse" -> "collapse"))(
        render.header +:
          model.filteredData.zipWithIndex.map(render.resultRowToHtml)
      )
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None
