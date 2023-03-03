package io.github.jisantuc.model

case class Model(
    filterBarStatus: Display,
    filters: Filters,
    data: List[ResultLine],
    filteredData: List[ResultLine],
    maxFilteredRound: Option[Int]
) {
  def updateFilters: Model = {
    val filteredData = filters.filterRows(data)
    val maxFilteredRound =
      filters.filterRowsWithoutRound(data).maxByOption(_.round).map(_.round)
    copy(filteredData = filteredData, maxFilteredRound = maxFilteredRound)
  }
}
