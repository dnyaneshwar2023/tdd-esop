package com.esop.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class STTRepositoryTest {

    @Test
    fun `it should add amount to STT tax`() {

        STTRepository.addTax(10)

        assertEquals(BigInteger.TEN,STTRepository.getTax())

    }
}
