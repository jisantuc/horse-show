package io.github.jisantuc.horseshow

import io.github.jisantuc.horseshow.model.Game
import io.github.jisantuc.horseshow.model.Msg
import io.github.jisantuc.horseshow.model.Result
import tyrian.CSS
import tyrian.Html
import tyrian.Html.*
import tyrian.Style

package object render {
  val comfy = "margin" -> ".25rem"

  def iconFor(game: Game): String =
    game match {
      case Game.NineBall  => "9️⃣"
      case Game.OnePocket => "1️⃣"
      case Game.BankPool  => "🏦"
    }

  def resultToHtml(row: Result, idx: Int): Html[Msg] =
    tr(_class := (if (idx % 2 == 0) "tr-even" else "tr-odd"))(
      td(_class := s"result-round cell ")(
        text(row.round.toString())
      ),
      td(_class := "result-game cell")(text(iconFor(row.game))),
      td(_class := "cell winner")(text(row.winner.name)),
      td(_class := "cell")(text(row.loser.name))
    )

  def header: Html[Msg] =
    tr(
      th(_class := "result-round cell")("Round"),
      th(_class := "result-game cell")("Game"),
      th(_class := "result-winner cell")("Winner"),
      th(_class := "result-loser cell")("Loser")
    )

}
