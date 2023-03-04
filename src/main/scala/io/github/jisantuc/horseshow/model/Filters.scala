package io.github.jisantuc.horseshow.model

final case class Filters(
    competitorNamePartStartsWith: Option[String],
    game: Option[Game],
    round: Option[Int]
) {

  private val gameFilter: Filters.ResultPredicate =
    game.fold(Filters.always)(g => _.game == g)
  private val roundFilter = round.fold(Filters.always)((r: Int) => _.round == r)
  private val competitorNameFilter =
    competitorNamePartStartsWith.fold(Filters.always)((n: String) =>
      val lowerPrefixes = n.toLowerCase().split(" ")
      (result: ResultLine) =>
        stringPartsMatch(
          result.winner.name,
          lowerPrefixes
        ) || stringPartsMatch(result.loser.name, lowerPrefixes)
    )

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

  def filterRows(rows: List[ResultLine]): List[ResultLine] =
    rows.filter(r => gameFilter(r) && roundFilter(r) && competitorNameFilter(r))

  def filterRowsWithoutRound(rows: List[ResultLine]): List[ResultLine] =
    rows.filter(r => gameFilter(r) && competitorNameFilter(r))
}

object Filters:
  type ResultPredicate = ResultLine => Boolean
  val always: ResultPredicate = _ => true
