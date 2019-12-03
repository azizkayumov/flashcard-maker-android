package com.piapps.flashcardpro.core.functional

/**
 * Created by abduaziz on 2019-09-22 at 00:13.
 */

sealed class Either<out L, out R> {

    class Left<out L>(val l: L) : Either<L, Nothing>()
    class Right<out R>(val r: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun callEither(leftFuntion: (L) -> Any, rightFunction: (R) -> Any) {
        when (this) {
            is Left -> leftFuntion(this.l)
            is Right -> rightFunction(this.r)
        }
    }
}