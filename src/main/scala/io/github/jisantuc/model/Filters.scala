package io.github.jisantuc.model

final case class Filters(
    competitorNamePartStartsWith: Option[String],
    game: Option[Game],
    round: Option[Int]
) {

  /** Check if any space-separated part of compare matches any prefix in
    * prefixes
    */
  private def stringPartsMatch(
      compare: String,
      prefixes: Array[String]
  ): Boolean = {
    val lowerCompares: Array[String] = compare.split(" ").map(_.toLowerCase())
    prefixes.foldLeft(true)((acc, s) =>
      acc && lowerCompares.map(_.startsWith(s)).foldLeft(false)(_ || _)
    )

  }
  def filterRows(rows: List[ResultLine]): List[ResultLine] = {
    val gameFilter: Filters.ResultPredicate =
      game.fold(Filters.always)(g => _.game == g)
    val roundFilter = round.fold(Filters.always)((r: Int) => _.round == r)
    val competitorNameFilter =
      competitorNamePartStartsWith.fold(Filters.always)((n: String) =>
        val lowerPrefixes = n.toLowerCase().split(" ")
        (result: ResultLine) =>
          stringPartsMatch(
            result.winner.name,
            lowerPrefixes
          ) || stringPartsMatch(result.loser.name, lowerPrefixes)
      )
    rows.filter(r => gameFilter(r) && roundFilter(r) && competitorNameFilter(r))
  }
}

object Filters:
  type ResultPredicate = ResultLine => Boolean
  val always: ResultPredicate = _ => true
