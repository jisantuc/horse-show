package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import tyrian.Html
import tyrian.Html.div

final case class RoundFilter() {
  def render: Html[Msg] = div("soon")
}
