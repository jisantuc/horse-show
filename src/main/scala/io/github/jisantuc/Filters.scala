package io.github.jisantuc

final case class Filters(
    competitorNameStartsWith: Option[Competitor],
    game: Option[Game],
    round: Option[Int]
)
