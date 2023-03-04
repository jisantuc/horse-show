package io.github.jisantuc.horseshow.components

import io.github.jisantuc.horseshow.model.Msg
import io.github.jisantuc.horseshow.render.comfy
import io.github.jisantuc.horseshow.render.flex
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
          onClick(Msg.FilterMsg.SelectRound(roundNum))
        )(s"$roundNum")
      )
    )
  def render: Html[Msg] = div(
    label(styles(comfy))(text("Round:")),
    select(styles(comfy))(roundsOptions: _*)
  )
}
