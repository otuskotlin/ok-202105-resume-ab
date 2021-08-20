package ru.otus.kotlin.resume.kmp

import kotlin.test.Test
import kotlin.test.assertTrue

class MainDTOTestLinux {
    @Test
    fun jsTest() {
        assertTrue("CommonClass.request must return \"Linux\""){
            CommonClass().request().contains("Linux")
        }
    }
}