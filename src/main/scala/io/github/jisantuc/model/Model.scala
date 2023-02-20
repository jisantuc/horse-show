package io.github.jisantuc.model

case class Model(
    filters: Filters,
    data: List[ResultLine],
    filteredData: List[ResultLine]
)
