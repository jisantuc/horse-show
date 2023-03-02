package io.github.jisantuc.components

import io.github.jisantuc.model.Game
import io.github.jisantuc.model.Msg
import io.github.jisantuc.render.comfy
import io.github.jisantuc.render.flex.flex
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
