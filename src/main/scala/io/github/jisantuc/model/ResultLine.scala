package io.github.jisantuc.model

case class ResultLine(
    round: Int,
    game: Game,
    winner: Competitor,
    loser: Competitor
)
