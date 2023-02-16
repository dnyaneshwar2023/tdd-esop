package com.esop.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class STTServiceTest {

    companion object {
        @JvmStatic
        fun getOrderQuantities(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(100, 100, 20),
                Arguments.of(10, 10, 1),
                Arguments.of(54, 21, 11)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantities")
    fun `It should calculate fee for sell order given order quantity is upto 100`(
        quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(quantity, price))
    }
}
