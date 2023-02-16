package com.esop.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class STTServiceTest {

    companion object {
        @JvmStatic
        fun getOrderQuantitiesUpto100(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(100, 100, 20),
                Arguments.of(10, 10, 1),
                Arguments.of(54, 21, 11)
            )
        }

        @JvmStatic
        fun getOrderQuantitiesMoreThan100LessThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(101, 100, 20),
                Arguments.of(1001, 1, 13),
                Arguments.of(540, 3, 20)
            )
        }

        @JvmStatic
        fun getOrderQuantitiesMoreThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(50_001, 100, 75002),
                Arguments.of(100_000, 100_000, 150000000),
                Arguments.of(54_000, 3, 2430)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesUpto100")
    fun `It should calculate fee for sell order given order quantity is upto 100`(
        quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesMoreThan100LessThan50k")
    fun `It should calculate fee for sell order given order quantity in range 101-50k`(
        quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesMoreThan50k")
    fun `It should calculate fee for sell order given order quantity is more than 50k`(
        quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(quantity, price))
    }
}
