package ru.otus.otuskotlin.validation

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.validation.cor.worker.validation
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty
import kotlin.test.assertEquals

class ValidationTest {
    @Test
    fun pipelineValidation() {
        val chain = chain<TestContext> {
            validation {
                errorHandler { v: ValidationResult ->
                    if(!v.isSuccess) {
                        errors.addAll(v.errors)
                    }
                }
                validate<String?> { validator(ValidatorStringNonEmpty()); on { x } }
                validate<String?> { validator(ValidatorStringNonEmpty()); on { y } }
            }
        }

        val context = TestContext()

        runBlocking {
            chain.build().exec(context)
            assertEquals(2, context.errors.size)
        }
    }

    data class TestContext(
        val x: String = "",
        val y: String = "",
        val errors: MutableList<IValidationError> = mutableListOf()
    )
}