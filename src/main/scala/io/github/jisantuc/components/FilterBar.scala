package io.github.jisantuc.components

import io.github.jisantuc.model.Display
import io.github.jisantuc.model.Msg
import io.github.jisantuc.render.flex.flexRowOf
import tyrian.Html
import tyrian.Html.*

case class FilterBar(
    display: Display,
    gameFilter: GameFilter,
    nameFilter: NameFilter,
    roundFilter: RoundFilter
) {
  def render: Html[Msg] = display match {
    case Display.On =>
      flexRowOf(
        Some("header-line"),
        gameFilter.render,
        roundFilter.render,
        nameFilter.render
      )
    case Display.Off =>
      div()
  }
}
