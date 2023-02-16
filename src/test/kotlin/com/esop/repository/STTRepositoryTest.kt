package com.esop.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigInteger

class STTRepositoryTest {

    @BeforeEach
    fun setUp() {
        STTRepository.resetTax()
    }

    @Test
    fun `it should add amount to STT tax`() {

        STTRepository.addTax(10)

        assertEquals(BigInteger.TEN,STTRepository.getTax())

    }
}
