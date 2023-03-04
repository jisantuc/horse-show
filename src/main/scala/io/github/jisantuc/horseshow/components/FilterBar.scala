package io.github.jisantuc.horseshow.components

import io.github.jisantuc.horseshow.model.Display
import io.github.jisantuc.horseshow.model.Msg
import io.github.jisantuc.horseshow.render.flex.flexRowOf
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
