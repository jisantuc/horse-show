package io.github.jisantuc.horseshow.components

import io.github.jisantuc.horseshow.model.Msg
import io.github.jisantuc.horseshow.model.Result
import io.github.jisantuc.horseshow.render.comfy
import io.github.jisantuc.horseshow.render.flex
import tyrian.Html
import tyrian.Html.*

final case class NameFilter(data: List[Result]) {
  def render: Html[Msg] = div(
    styles(flex.flex)
  )(
    label(styles(comfy))(text("Competitor:")),
    input[Msg](onInput(Msg.FilterMsg.NameIncludes(_)), `id` := "autoComplete")
  )
}
