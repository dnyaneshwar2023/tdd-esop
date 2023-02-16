package com.esop.service

import kotlin.math.min
import kotlin.math.round

class STTService {
    fun computeTax(quantity: Long, price: Long): Long {
        return when {
            quantity <= 100 -> min(round(quantity * price * 0.01).toLong(), 20)
            else -> 0
        }
    }


}