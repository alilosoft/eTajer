package demos

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class NullableTypes {
    @Test
    fun `nullable string`() {
        val nullable : String? = null;
        Assertions.assertEquals("Null string!", nullable?.length ?: "Null string!")
        Assertions.assertNull(nullable?.length)
        assertThrows<NullPointerException> { println(nullable!!.length) }
    }
}