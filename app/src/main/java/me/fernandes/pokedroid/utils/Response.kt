package me.fernandes.pokedroid.utils

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null,
    val codeError: Int? = null
) {
    class Loading<T>(data: T? = null) : Response<T>(data)
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(message: String, data: T? = null) : Response<T>(data, message)
    class NetworkError<T>(codeError: Int?, message: String, data: T? = null) :
        Response<T>(data, message, codeError)

}
