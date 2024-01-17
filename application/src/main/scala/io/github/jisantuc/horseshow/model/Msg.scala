package io.github.jisantuc.horseshow.model

sealed abstract class Msg

object Msg:
  object FilterMsg:
    case class PickGame(game: Game)           extends Msg
    case object ClearGameFilter               extends Msg
    case class NameIncludes(namePart: String) extends Msg
    case object ToggleFilterDisplay           extends Msg
    case class SelectRound(round: Int)        extends Msg

  case class RefreshDataSucceeded(rows: List[ResultLine]) extends Msg
  case object RefreshDataFailed                           extends Msg
  case object RefreshData                                 extends Msg
