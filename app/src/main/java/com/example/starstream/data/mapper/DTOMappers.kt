package com.example.starstream.data.mapper

import com.example.starstream.data.remote.dto.GenreDTO
import com.example.starstream.data.remote.dto.ImageDTO
import com.example.starstream.data.remote.dto.ImageListDTO
import com.example.starstream.data.remote.dto.LanguageDTO
import com.example.starstream.data.remote.dto.MovieDTO
import com.example.starstream.data.remote.dto.MovieDetailDTO
import com.example.starstream.data.remote.dto.MovieListDTO
import com.example.starstream.data.remote.dto.VideoDTO
import com.example.starstream.data.remote.dto.VideoListDTO
import com.example.starstream.domain.model.Genre
import com.example.starstream.domain.model.Image
import com.example.starstream.domain.model.ImageList
import com.example.starstream.domain.model.Language
import com.example.starstream.domain.model.Movie
import com.example.starstream.domain.model.MovieDetail
import com.example.starstream.domain.model.MovieList
import com.example.starstream.domain.model.Person
import com.example.starstream.domain.model.PersonList
import com.example.starstream.domain.model.Video
import com.example.starstream.domain.model.VideoList


internal fun GenreDTO.toGenre() = Genre(id, name)

internal fun ImageDTO.toImage() = Image(filePath)

internal fun ImageListDTO.toImageList() = ImageList(backdrops?.map { it.toImage() }, posters?.map { it.toImage() }, profiles?.map { it.toImage() }, stills?.map { it.toImage() })

internal fun LanguageDTO.toLanguage() = Language(englishName)

internal fun MovieDTO.toMovie() = Movie(character, id, job, overview, posterPath, releaseDate, title, voteAverage)


internal fun MovieDetailDTO.toMovieDetail() = MovieDetail(
    budget,
    genres.map { it.toGenre() },
    homepage,
    id,
    images.toImageList(),
    originalTitle,
    overview,
    posterPath,
    recommendations.toMovieList(),
    releaseDate,
    revenue,
    runtime,
    spokenLanguages.map { it.toLanguage() },
    status,
    title,
    videos.toVideoList(),
    voteAverage,
    voteCount
)

internal fun MovieListDTO.toMovieList() = MovieList(results.map { it.toMovie() }, totalResults)

internal fun VideoDTO.toVideo() = Video(key, name, publishedAt, site, type)

internal fun VideoListDTO.toVideoList() = VideoList(results.map { it.toVideo() })