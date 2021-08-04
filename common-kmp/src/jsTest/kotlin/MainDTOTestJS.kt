package ru.otus.kotlin.resume.kmp

import kotlin.test.Test
import kotlin.test.assertTrue

class MainDTOTestJS {
    @Test
    fun jsTest() {
        assertTrue("CommonClass.request must return \"JS\""){
            CommonClass().request().contains("JS")
        }
    }
}