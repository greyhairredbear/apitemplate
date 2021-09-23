@file:Suppress("MagicNumber", "TooGenericExceptionThrown")

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.delay

// ENTRY POINT, FRAMEWORKS & DRIVERS

suspend fun main() {
    reservationUseCase(
        UseCaseData(
            requestedSeats = 5,
            reservationName = "John Dorian",
            ::getCurrentlyReservedSeats,
            ::saveReservation,
        )
    ).invoke().fold(
        ifLeft = { throw Exception(it.toString()) },
        ifRight = { println(it.newRecordId) },
    )

    reservationUseCaseBind(
        UseCaseData(
            requestedSeats = 5,
            reservationName = "John Dorian",
            ::getCurrentlyReservedSeats,
            ::saveReservation,
        )
    ).fold(
        ifLeft = { throw Exception(it.toString()) },
        ifRight = { println(it.newRecordId) },
    )
}

abstract class DbError : Error()
object ReadError : DbError()
object WriteError : DbError()

suspend fun getCurrentlyReservedSeats(): Either<ReadError, Int> {
    // ... get stuff from db
    delay(1)
    return 4.right()
}

suspend fun saveReservation(value: String, reservationPossible: ReservationPossible): Either<WriteError, Long> {
    // ... writing something to db
    delay(1)
    return 42L.right() // newRecordId
}

// USE CASES

data class UseCaseData(
    val requestedSeats: Int,
    val reservationName: String,
    val getCurrentlyReservedSeats: suspend () -> Either<ReadError, Int>,
    val writeVal: suspend (String, ReservationPossible) -> Either<WriteError, Long>,
)

data class UseCaseResultData(val newRecordId: Long)

fun reservationUseCase(data: UseCaseData): suspend () -> Either<Error, UseCaseResultData> = {
    data.getCurrentlyReservedSeats()
        .flatMap { reservationPossible(data.requestedSeats, it, CAPACITY) }
        .flatMap { data.writeVal(data.reservationName, it) }
        .flatMap { UseCaseResultData(it).right() }
}

suspend fun reservationUseCaseBind(data: UseCaseData): Either<Error, UseCaseResultData> =
    either {
        val reservedSeats = data.getCurrentlyReservedSeats().bind()
        val reservationPossible = reservationPossible(data.requestedSeats, reservedSeats, CAPACITY).bind()
        val newReservationId = data.writeVal(data.reservationName, reservationPossible).bind()

        UseCaseResultData(newReservationId)
    }

// ENTITIES

const val CAPACITY = 10

object RequestedTooManySeats : Error()
sealed class Error

data class ReservationPossible(val newNumberOfReservedSeats: Int)

fun reservationPossible(
    requestedSeats: Int,
    reservedSeats: Int,
    capacity: Int
): Either<RequestedTooManySeats, ReservationPossible> =
    if (reservedSeats + requestedSeats <= capacity) {
        ReservationPossible(requestedSeats + reservedSeats).right()
    } else {
        RequestedTooManySeats.left()
    }
