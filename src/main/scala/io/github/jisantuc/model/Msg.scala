package io.github.jisantuc.model

sealed abstract class Msg

object Msg:
  case class DataReceived(rows: List[ResultLine]) extends Msg
  case object ToggleGameFilter                    extends Msg
