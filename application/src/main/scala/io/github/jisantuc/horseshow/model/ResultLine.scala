package io.github.jisantuc.horseshow.model

case class ResultLine(
    round: Int,
    game: Game,
    winner: Competitor,
    loser: Competitor
)
