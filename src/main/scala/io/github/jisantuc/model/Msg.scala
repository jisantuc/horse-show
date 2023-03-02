package io.github.jisantuc.model

sealed abstract class Msg

object Msg:
  object FilterMsg:
    case class PickGame(game: Game)           extends Msg
    case object ClearGameFilter               extends Msg
    case class NameIncludes(namePart: String) extends Msg
    case object ToggleFilterDisplay           extends Msg
    case object IncrementRound                extends Msg
    case object DecrementRound                extends Msg

  case class DataReceived(rows: List[ResultLine]) extends Msg
