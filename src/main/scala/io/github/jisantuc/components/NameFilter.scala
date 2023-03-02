package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import io.github.jisantuc.model.ResultLine
import io.github.jisantuc.render.flex
import tyrian.Html
import tyrian.Html.div
import tyrian.Html.id
import tyrian.Html.input
import tyrian.Html.onInput
import tyrian.Html.styles

final case class NameFilter(data: List[ResultLine]) {
  def render: Html[Msg] = div(
    styles(flex.flex)
  )(
    input[Msg](onInput(Msg.NameIncludes(_)), `id` := "autoComplete")
  )
}
