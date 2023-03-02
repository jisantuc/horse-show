package io.github.jisantuc.model

case class Model(
    filterBarStatus: Display,
    filters: Filters,
    data: List[ResultLine],
    filteredData: List[ResultLine]
) {
  def updateFilters: Model =
    copy(filteredData = filters.filterRows(data))
}
