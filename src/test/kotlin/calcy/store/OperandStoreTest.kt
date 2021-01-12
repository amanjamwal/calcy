package calcy.store

import calcy.LimitReachedException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

internal class OperandStoreTest {

    @Test
    @DisplayName("Check empty operand store")
    fun emptyOperandStore() {
        val operandStore = OperandStore()
        assertEquals(0, operandStore.size())
        assertTrue(operandStore.getState().isEmpty())
    }

    @Test
    @DisplayName("Check adding data to operand store")
    fun enterSingleValue() {
        val anyValue = BigDecimal.valueOf(10)
        val operandStore = OperandStore()
        operandStore.enter(anyValue)
        assertEquals(1, operandStore.size())
    }

    @Test
    @DisplayName("Check adding data to full operand store")
    fun enterValueToFullStore() {
        val operandStore = OperandStore(1)
        operandStore.enter(BigDecimal.valueOf(10))

        assertThrows<LimitReachedException> { operandStore.enter(BigDecimal.valueOf(10)) }
    }

    @Test
    @DisplayName("Check removing value from store")
    fun removeValueFromStore() {
        val operandStore = OperandStore()
        operandStore.enter(BigDecimal.valueOf(10))
        assertEquals(1, operandStore.size())

        operandStore.remove()
        assertEquals(0, operandStore.size())
    }

    @Test
    @DisplayName("Check clearing values from store")
    fun clear() {
        val operandStore = OperandStore()
        operandStore.enter(BigDecimal.valueOf(10))
        operandStore.enter(BigDecimal.valueOf(20))
        assertEquals(2, operandStore.size())

        operandStore.clear()
        assertEquals(0, operandStore.size())
    }

    @Test
    @DisplayName("Check restoring values of store")
    fun restore() {
        val operandStore = OperandStore()
        operandStore.enter(BigDecimal.valueOf(10))
        operandStore.enter(BigDecimal.valueOf(20))
        assertEquals(2, operandStore.size())

        val valuesToRestore = listOf(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO)
        operandStore.restore(valuesToRestore)
        assertEquals(valuesToRestore.size, operandStore.size())
    }

    @Test
    @DisplayName("Check state of store")
    fun getState() {
        val startState = listOf(BigDecimal.valueOf(10), BigDecimal.valueOf(20))
        val operandStore = OperandStore()
        startState.forEach { value -> operandStore.enter(value) }
        assertEquals(startState.size, operandStore.size())

        val currentState = operandStore.getState()
        assertEquals(startState.size, currentState.size)

        for (i in startState.indices) {
            assertEquals(startState[i].toInt(), currentState[i].toInt())
        }
    }

    @Test
    @DisplayName("Check to string of store")
    fun testToString() {
        val startState = listOf(10, 20)
        val operandStore = OperandStore()
        startState.forEach { value -> operandStore.enter(BigDecimal(value)) }
        assertEquals(startState.size, operandStore.size())

        val expectedValue = startState.joinToString(" ", "Stack: ")
        assertEquals(expectedValue, operandStore.toString())
    }
}
