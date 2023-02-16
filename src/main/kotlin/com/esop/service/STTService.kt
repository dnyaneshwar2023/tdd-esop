package com.esop.service

import com.esop.exceptions.TotalValueOverflowException
import java.lang.IllegalArgumentException
import kotlin.math.min
import kotlin.math.round

class STTService {
    fun computeTax(esopType: String, quantity: Long, price: Long): Long {
        validateArguments(quantity, price, esopType)
        return if (esopType == "NON_PERFORMANCE") {
            when {
                quantity <= 100 -> min(round(quantity * price * 0.01).toLong(), 20)
                quantity in 101..50_000 -> min(round(quantity * price * 0.0125).toLong(), 20)
                else -> round(quantity * price * 0.015).toLong()
            }
        } else {
            when {
                quantity <= 100 -> min(round(quantity * price * 0.02).toLong(), 50)
                quantity in 101..50_000 -> round(quantity * price * 0.0225).toLong()
                else -> round(quantity * price * 0.025).toLong()
            }
        }
    }

    private fun validateArguments(quantity: Long, price: Long, esopType: String) {
        if (quantity < 0 || price < 0) {
            throw IllegalArgumentException("Quantity or Price cannot be negative")
        }

        if (esopType != "PERFORMANCE" && esopType != "NON_PERFORMANCE") {
            throw IllegalArgumentException("Esop type is not valid. Valid Types: PERFORMANCE, NON_PERFORMANCE")
        }

        if (quantity > Long.MAX_VALUE / price) {
            throw TotalValueOverflowException("Overflow in total value of price * quantity")
        }
    }
}
