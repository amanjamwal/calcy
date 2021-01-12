package calcy.store

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock

internal class OperationHistoryTest {

    @Mock
    private lateinit var mockOperation: Operation

    @BeforeEach
    fun beforeEach() {
        mockOperation = mock()
    }

    @Test
    @DisplayName("Check push operation")
    fun push() {
        val operationHistory = OperationHistory()
        operationHistory.push(mockOperation)
    }

    @Test
    @DisplayName("Check pop operation")
    fun pop() {
        val operationHistory = OperationHistory()
        operationHistory.push(mockOperation)

        val actualOperation = operationHistory.pop()
        assertEquals(mockOperation, actualOperation)
    }

    @Test
    @DisplayName("Check pop operation from empty store")
    fun popFromEmpty() {
        val operationHistory = OperationHistory()
        val actualOperation = operationHistory.pop()
        assertNull(actualOperation)
    }
}
