package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import io.github.jisantuc.render.comfy
import io.github.jisantuc.render.flex
import tyrian.Html
import tyrian.Html.*

final case class RoundFilter(currentRound: Option[Int]) {
  def render: Html[Msg] = div(
    label(styles(comfy))(text("Round:")),
    button(styles(comfy), onClick(Msg.FilterMsg.DecrementRound))("-"),
    span(styles(comfy))(currentRound.fold("all")(i => s"${i}")),
    button(styles(comfy), onClick(Msg.FilterMsg.IncrementRound))("+")
  )
}
