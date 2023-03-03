package io.github.jisantuc.components

import io.github.jisantuc.model.Msg
import io.github.jisantuc.render.comfy
import io.github.jisantuc.render.flex
import tyrian.Html
import tyrian.Html.*

final case class RoundFilter(currentRound: Option[Int], maxRound: Option[Int]) {
  private val roundRange: Option[Range.Inclusive] = maxRound.map(1 to _)
  private val noRoundFilter =
    option(onClick(Msg.FilterMsg.SelectRound(0)))("all")
  private val roundsOptions: List[Html[Msg]] =
    roundRange.fold(List(noRoundFilter))(range =>
      noRoundFilter +: range.toList.map(roundNum =>
        option(
          onClick(Msg.FilterMsg.SelectRound(roundNum)),
          selected(Some(roundNum) == currentRound)
        )(s"$roundNum")
      )
    )
  def render: Html[Msg] = div(
    label(styles(comfy))(text("Round:")),
    select(styles(comfy))(roundsOptions: _*)
  )
}
