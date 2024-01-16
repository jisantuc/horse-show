package io.github.jisantuc.horseshow.model

/** Like {@link ResultLine}, but with a winner and loser */
case class Result(
    round: Int,
    game: Game,
    winner: Competitor,
    loser: Competitor
)
