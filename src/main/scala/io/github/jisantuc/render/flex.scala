package io.github.jisantuc.render

import tyrian.Html
import tyrian.Html.*

object flex:
  val flex         = "display"         -> "flex"
  val spaceBetween = "justify-content" -> "space-between"
  val spaceAround  = "justify-content" -> "space-around"

  def flexRowOf[M](children: Html[M]*): Html[M] =
    div(_class := "flex-row", styles(spaceBetween))(children: _*)
