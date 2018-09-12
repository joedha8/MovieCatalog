package id.rackspira.moviecatalog.model

data class BaseSearch(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<Result>
)