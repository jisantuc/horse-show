package io.github.jisantuc.horseshow.render

import tyrian.Html
import tyrian.Html.*

object flex:
  val flex         = "display"         -> "flex"
  val spaceBetween = "justify-content" -> "space-between"
  val spaceAround  = "justify-content" -> "space-around"

  def flexRowOf[M](extraClasses: Option[String], children: Html[M]*): Html[M] =
    div(
      _class := extraClasses.fold("flex-row")(s => s"flex-row $s"),
      styles(spaceBetween)
    )(children: _*)
