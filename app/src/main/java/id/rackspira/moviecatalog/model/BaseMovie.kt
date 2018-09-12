package id.rackspira.moviecatalog.model

data class BaseMovie(
        val results: List<Result>,
        val page: Int,
        val total_results: Int,
        val dates: Dates,
        val total_pages: Int
)