package com.iwritebug.data

import com.iwritebug.domain.Either
import com.iwritebug.domain.Failure
import com.iwritebug.domain.Movie
import com.iwritebug.domain.MoviesRepository
import retrofit2.Call
import javax.inject.Inject

class Network
@Inject constructor(private val networkHandler: NetworkHandler,
                    private val service: MoviesService) : MoviesRepository {

    override fun movies(): Either<Failure, List<Movie>> {
        return when (networkHandler.isConnected) {
            true -> request(service.movies(), { it.map { it.toMovie() } }, emptyList())
            false, null -> Either.Left(Failure.NetworkConnection())
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError())
        }
    }
}