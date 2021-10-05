package ru.otus.otuskotlin.resume.cor

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class CorJvmTest {

    @Test
    fun corTest() {
        val chain = CorTest.chain
        val ctx = TestContext(some = 13)

        runBlocking { chain.exec(ctx) }

        assertEquals(17, ctx.some)
    }
}