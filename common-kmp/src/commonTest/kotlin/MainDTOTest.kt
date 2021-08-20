import ru.otus.kotlin.resume.kmp.CommonClass
import ru.otus.kotlin.resume.kmp.MainDTO
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainDTOTest {

    @Test
    fun fistTest() {
        val actual = MainDTO(firstName = "Common", secondName = "Common")
        assertEquals("Common", actual.firstName)
        assertEquals("Common", actual.secondName)
    }

    fun commonTest() {
        assertTrue("CommonClass.request must return \"Req with \""){
            CommonClass().request().contains("Req with ")
        }
    }
}