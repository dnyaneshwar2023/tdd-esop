package com.esop.service

import com.esop.exceptions.TotalValueOverflowException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.IllegalArgumentException
import java.util.stream.Stream

class STTServiceTest {

    companion object {
        @JvmStatic
        fun getOrderQuantitiesUpto100(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("NON_PERFORMANCE", 100, 100, 20),
                Arguments.of("NON_PERFORMANCE", 10, 10, 1),
                Arguments.of("NON_PERFORMANCE", 54, 21, 11)
            )
        }

        @JvmStatic
        fun getOrderQuantitiesMoreThan100LessThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("NON_PERFORMANCE", 101, 100, 20),
                Arguments.of("NON_PERFORMANCE", 1001, 1, 13),
                Arguments.of("NON_PERFORMANCE", 540, 3, 20)
            )
        }

        @JvmStatic
        fun getOrderQuantitiesMoreThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("NON_PERFORMANCE", 50_001, 100, 75002),
                Arguments.of("NON_PERFORMANCE", 100_000, 100_000, 150000000),
                Arguments.of("NON_PERFORMANCE", 54_000, 3, 2430)
            )
        }

        @JvmStatic
        fun getPerformanceOrderQuantitiesUpto100(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("PERFORMANCE", 100, 100, 50),
                Arguments.of("PERFORMANCE", 10, 10, 2),
                Arguments.of("PERFORMANCE", 54, 21, 23)
            )
        }

        @JvmStatic
        fun getPerformanceOrderQuantitiesMoreThan100LessThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("PERFORMANCE", 101, 100, 227),
                Arguments.of("PERFORMANCE", 1001, 1, 23),
                Arguments.of("PERFORMANCE", 540, 3, 36)
            )
        }

        @JvmStatic
        fun getPerformanceOrderQuantitiesMoreThan50k(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("PERFORMANCE", 50_001, 100, 125002),
                Arguments.of("PERFORMANCE", 100_000, 100_000, 250000000),
                Arguments.of("PERFORMANCE", 54_000, 3, 4050)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesUpto100")
    fun `It should calculate fee for sell order given order quantity is upto 100`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesMoreThan100LessThan50k")
    fun `It should calculate fee for sell order given order quantity in range 101-50k`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getOrderQuantitiesMoreThan50k")
    fun `It should calculate fee for sell order given order quantity is more than 50k`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }


    @ParameterizedTest
    @MethodSource("getPerformanceOrderQuantitiesUpto100")
    fun `It should calculate fee for performance sell order given order quantity is upto 100`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getPerformanceOrderQuantitiesMoreThan100LessThan50k")
    fun `It should calculate fee for performance sell order given order quantity in range 101-50k`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }

    @ParameterizedTest
    @MethodSource("getPerformanceOrderQuantitiesMoreThan50k")
    fun `It should calculate fee for performance sell order given order quantity is more than 50k`(
        esopType: String, quantity: Long,
        price: Long,
        expectedTax: Long
    ) {
        assertEquals(expectedTax, STTService().computeTax(esopType, quantity, price))
    }

    @Test
    fun `It should throw exception given quantity is negative`() {
        assertThrows(IllegalArgumentException::class.java) { STTService().computeTax("NON_PERFORMANCE", -10, 20) }
    }
    @Test
    fun `It should throw exception given price is negative`() {
        assertThrows(IllegalArgumentException::class.java) { STTService().computeTax("NON_PERFORMANCE", 10, -20) }
    }

    @Test
    fun `It should throw exception if given esopType is invalid`() {
        assertThrows(IllegalArgumentException::class.java) { STTService().computeTax("INVALID_ESOP", 10, -20) }
    }

    @Test
    fun `It should throw exception if price multiplied quantity overflows`() {
        assertThrows(TotalValueOverflowException::class.java) { STTService().computeTax("PERFORMANCE", 10_000_000_00_000, 190_000_000_000_000) }
    }

}
