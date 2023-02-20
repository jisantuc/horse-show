package io.github.jisantuc.model

final case class Filters(
    competitorNameStartsWith: Option[String],
    game: Option[Game],
    round: Option[Int]
) {
  def filterRows(rows: List[ResultLine]): List[ResultLine] = {
    val gameFilter  = game.fold(Filters.always)((g: Game) => _.game == g)
    val roundFilter = round.fold(Filters.always)((r: Int) => _.round == r)
    val competitorNameFilter =
      competitorNameStartsWith.fold(Filters.always)((n: String) =>
        (result: ResultLine) =>
          (result.winner.name.startsWith(n) || result.loser.name
            .startsWith(n))
      )
    rows.filter(r => gameFilter(r) && roundFilter(r) && competitorNameFilter(r))
  }
}

object Filters:
  type ResultPredicate = ResultLine => Boolean
  val always: ResultPredicate = _ => true
