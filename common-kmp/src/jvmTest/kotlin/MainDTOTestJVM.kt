package ru.otus.kotlin.resume.kmp

import kotlin.test.Test
import kotlin.test.assertTrue

class MainDTOTestJVM {
    @Test
    fun jsTest() {
        assertTrue("CommonClass.request must return \"JVM\""){
            CommonClass().request().contains("JVM")
        }
    }
}