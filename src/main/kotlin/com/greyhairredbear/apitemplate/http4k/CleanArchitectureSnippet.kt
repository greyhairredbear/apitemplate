@file:Suppress("MagicNumber", "TooGenericExceptionThrown")

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.delay

// ENTRY POINT, FRAMEWORKS & DRIVERS

suspend fun main() {
    execReservationUseCase(
        UseCaseData(
            requestedSeats = 5,
            reservationName = "John Dorian",
            ::getCurrentlyReservedSeats,
            ::saveReservation,
        )
    )().fold(
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

suspend fun saveReservation(value: String): Either<WriteError, Long> {
    // ... writing something to db
    delay(1)
    return 42L.right() // newRecordId
}

// USE CASES

data class UseCaseData(
    val requestedSeats: Int,
    val reservationName: String,
    val readVal: suspend () -> Either<ReadError, Int>,
    val writeVal: suspend (String) -> Either<WriteError, Long>,
)

data class UseCaseResultData(val newRecordId: Long)

fun execReservationUseCase(data: UseCaseData): suspend () -> Either<Error, UseCaseResultData> = {
    data.readVal()
        .flatMap { reservationPossible(data.requestedSeats, it, CAPACITY) }
        .flatMap { data.writeVal(data.reservationName) }
        .flatMap { UseCaseResultData(it).right() }
}

// ENTITIES

const val CAPACITY = 10

object RequestedTooManySeats : Error()
sealed class Error

fun reservationPossible(
    requestedSeats: Int,
    reservedSeats: Int,
    capacity: Int
): Either<RequestedTooManySeats, Unit> =
    if (reservedSeats + requestedSeats <= capacity) Unit.right() else RequestedTooManySeats.left()
