package io.github.jisantuc

import io.github.jisantuc.model.Game
import io.github.jisantuc.model.Msg
import io.github.jisantuc.model.ResultLine
import tyrian.CSS
import tyrian.Html
import tyrian.Html.*
import tyrian.Style

object render:

  val flex = "display" -> "flex"

  def iconFor(game: Game): String =
    game match {
      case Game.NineBall  => "9️⃣"
      case Game.OnePocket => "1️⃣"
      case Game.BankPool  => "🏦"
    }
  def resultRowToHtml(row: ResultLine): Html[Msg] =
    div(styles("display" -> "flex", "flex-direction" -> "row"))(
      div(styles(flex), `class` := "round-number cell")(
        text(row.round.toString())
      ),
      div(styles(flex), `class` := "result-game cell")(text(iconFor(row.game))),
      div(styles(flex), `class` := "result-winner cell")(text(row.winner.name)),
      div(styles(flex), `class` := "result-loser cell")(text(row.loser.name))
    )
