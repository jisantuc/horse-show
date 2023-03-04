package io.github.jisantuc.horseshow.components

import io.github.jisantuc.horseshow.model.Game
import io.github.jisantuc.horseshow.model.Msg
import io.github.jisantuc.horseshow.render.comfy
import io.github.jisantuc.horseshow.render.flex.flex
import tyrian.Html
import tyrian.Html.*

final case class GameFilter(
    selectedGame: Option[Game]
) {
  def render: Html[Msg] = div(styles(flex))(
    label(styles(flex, comfy))(text("Game:")),
    select(styles(flex, comfy))(
      option(onClick(Msg.FilterMsg.ClearGameFilter))(text("all")) +:
        Game.values.toList.map(g =>
          option(
            onClick(Msg.FilterMsg.PickGame(g))
          )(text(s"$g"))
        )
    )
  )
}
