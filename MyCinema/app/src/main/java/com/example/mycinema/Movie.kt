package com.example.mycinema

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Movie(
    val id: String,
    @StringRes val title: Int,
    @StringRes val genre: Int,
    @StringRes val synopsis: Int,
    @DrawableRes val poster: Int,
    val rating: Double,
    val matchPercent: Int
)

object MovieRepository {

    const val DEFAULT_MOVIE_ID = "diehard"

    private val movies = listOf(
        Movie(
            id = "diehard",
            title = R.string.diehard_title,
            genre = R.string.diehard_genre,
            synopsis = R.string.diehard_synopsis,
            poster = R.drawable.diehard,
            rating = 4.9,
            matchPercent = 95
        ),
        Movie(
            id = "atlantis",
            title = R.string.atlantis_title,
            genre = R.string.atlantis_genre,
            synopsis = R.string.atlantis_synopsis,
            poster = R.drawable.atlantis,
            rating = 4.3,
            matchPercent = 88
        ),
        Movie(
            id = "cars",
            title = R.string.cars_title,
            genre = R.string.cars_genre,
            synopsis = R.string.cars_synopsis,
            poster = R.drawable.cars,
            rating = 4.6,
            matchPercent = 91
        )
    )

    fun all(): List<Movie> = movies

    fun byId(id: String?): Movie = movies.firstOrNull { it.id == id } ?: movies.first()

    fun similarTo(movie: Movie): List<Movie> = movies.filter { it.id != movie.id }
}
