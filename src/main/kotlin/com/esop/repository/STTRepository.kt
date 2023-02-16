package com.esop.repository

import java.math.BigInteger


object STTRepository {
    private var sttTax: BigInteger = BigInteger.ZERO

    fun addTax(amount: Long) {
        sttTax += BigInteger.valueOf(amount)
    }

    fun getTax(): BigInteger = sttTax
    fun resetTax() {
        sttTax = BigInteger.ZERO
    }
}
