package demos

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KoptionalDemo {
    @Test
    fun `null toOptional() should create Non object`() {
        // Arrange
        val nullVal = Optional.toOptional(null)
        // Act
        // Assert
        Assertions.assertEquals(None, nullVal)
        Assertions.assertNull(nullVal.toNullable())
    }

    @Test
    fun `Some(String) & String toOptional() should be equals`() {
        // Arrange
        val name1 = Some("alilo")
        val name2 = "alilo".toOptional()
        // Act
        // Assert
        Assertions.assertEquals(name1, name2)
    }
}