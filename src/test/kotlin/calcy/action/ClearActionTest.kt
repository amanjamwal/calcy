package calcy.action

import calcy.store.OperandStore
import calcy.store.OperationHistory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.math.BigDecimal

internal class ClearActionTest {

    @Mock
    private lateinit var mockOperandStore: OperandStore

    @Mock
    private lateinit var mockOperationHistory: OperationHistory

    @BeforeEach
    fun setUp() {
        mockOperandStore = mock()
        mockOperationHistory = mock()
    }

    @Test
    @DisplayName("Check Identifier")
    fun getIdentifier() {
        val clearAction = ClearAction(mockOperandStore, mockOperationHistory)
        assertEquals("clear", clearAction.getIdentifier())
    }

    @Test
    @DisplayName("Check execute for happy path")
    fun executeHappyPath() {
        val state = listOf(BigDecimal(10), BigDecimal(20))
        val clearAction = ClearAction(mockOperandStore, mockOperationHistory)
        Mockito.`when`(mockOperandStore.getState()).thenReturn(state)

        clearAction.execute()

        verify(mockOperandStore, times(1)).clear()
        verify(mockOperationHistory, times(1)).push(any())
    }

    @Test
    @DisplayName("Check execute when store is already empty")
    fun clearEmptyStore() {
        val clearAction = ClearAction(mockOperandStore, mockOperationHistory)
        Mockito.`when`(mockOperandStore.getState()).thenReturn(listOf())

        clearAction.execute()

        verify(mockOperandStore, times(0)).clear()
        verify(mockOperationHistory, times(0)).push(any())
    }
}
