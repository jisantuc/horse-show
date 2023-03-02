package io.github.jisantuc.model

sealed abstract class Msg

object Msg:
  case class DataReceived(rows: List[ResultLine]) extends Msg
  case class PickGame(game: Game)                 extends Msg
  case class NameIncludes(namePart: String)       extends Msg
  case object ClearGameFilter                     extends Msg
  case object ToggleFilterDisplay                 extends Msg
