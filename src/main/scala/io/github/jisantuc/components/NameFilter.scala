package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import tyrian.Html
import tyrian.Html.div

final case class NameFilter() {
  def render: Html[Msg] = div("soon")
}
