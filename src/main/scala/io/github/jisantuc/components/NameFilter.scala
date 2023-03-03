package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import io.github.jisantuc.model.ResultLine
import io.github.jisantuc.render.comfy
import io.github.jisantuc.render.flex
import tyrian.Html
import tyrian.Html.*

final case class NameFilter(data: List[ResultLine]) {
  def render: Html[Msg] = div(
    styles(flex.flex)
  )(
    label(styles(comfy))(text("Competitor:")),
    input[Msg](onInput(Msg.FilterMsg.NameIncludes(_)), `id` := "autoComplete")
  )
}
