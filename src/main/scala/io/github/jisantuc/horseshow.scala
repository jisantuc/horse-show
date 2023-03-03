package io.github.jisantuc

import cats.effect.IO
import io.github.jisantuc.components.FilterBar
import io.github.jisantuc.components.GameFilter
import io.github.jisantuc.components.NameFilter
import io.github.jisantuc.components.RoundFilter
import io.github.jisantuc.model.*
import io.github.jisantuc.render.flex
import monocle.syntax.all._
import tyrian.Html.*
import tyrian.*

import scala.scalajs.js.annotation.*

@JSExportTopLevel("TyrianApp")
object horseshow extends TyrianApp[Msg, Model]:

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
      Model(
        Display.On,
        Filters(None, None, None),
        someLines,
        someLines,
        someLines.maxByOption(_.round).map(_.round)
      ),
      Cmd.None
    )

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
    case Msg.DataReceived(rows) => (model, Cmd.None)
    case Msg.FilterMsg.SelectRound(0) =>
      val updated = model
        .focus(_.filters.round)
        .replace(None)
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.SelectRound(n)
        if model.maxFilteredRound.map(_ < n).getOrElse(false) =>
      val updated = model
        .focus(_.filters.round)
        .replace(None)
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.SelectRound(n) =>
      val updated = model
        .focus(_.filters.round)
        .replace(Some(n))
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.PickGame(g) =>
      val updated = model.focus(_.filters.game).replace(Some(g))
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.NameIncludes("") =>
      val updated =
        model.focus(_.filters.competitorNamePartStartsWith).replace(None)
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.NameIncludes(nameParts) =>
      val updated = model
        .focus(_.filters.competitorNamePartStartsWith)
        .replace(Some(nameParts))
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.ClearGameFilter =>
      val updated = model.focus(_.filters.game).replace(None)
      (updated.updateFilters, Cmd.None)

    case Msg.FilterMsg.ToggleFilterDisplay =>
      (model.focus(_.filterBarStatus).modify(_.toggle), Cmd.None)
  }

  def view(model: Model): Html[Msg] =
    div(_class := "flex-column")(
      button(
        onClick(Msg.FilterMsg.ToggleFilterDisplay),
        _class := "header-line"
      )(
        "Show filters"
      ),
      FilterBar(
        model.filterBarStatus,
        GameFilter(model.filters.game),
        NameFilter(model.data),
        RoundFilter(model.filters.round, model.maxFilteredRound)
      ).render,
      table(styles("border-collapse" -> "collapse"))(
        render.header +:
          model.filteredData.zipWithIndex.map(render.resultRowToHtml)
      )
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None
