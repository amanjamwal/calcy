package calcy.action

import calcy.store.OperandStore
import calcy.store.Operation
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

internal class UndoActionTest {

    @Mock
    private lateinit var mockOperandStore: OperandStore

    @Mock
    private lateinit var mockOperationHistory: OperationHistory

    @Mock
    private lateinit var mockOperation: Operation

    @BeforeEach
    fun setUp() {
        mockOperandStore = mock()
        mockOperationHistory = mock()
        mockOperation = mock()
    }

    @Test
    @DisplayName("Check Identifier")
    fun getIdentifier() {
        val undoAction = UndoAction(mockOperandStore, mockOperationHistory)
        assertEquals("undo", undoAction.getIdentifier())
    }

    @Test
    @DisplayName("Check execute for happy path")
    fun executeHappyPath() {
        val undoAction = UndoAction(mockOperandStore, mockOperationHistory)
        Mockito.`when`(mockOperationHistory.pop()).thenReturn(mockOperation)

        undoAction.execute()

        verify(mockOperationHistory, times(1)).pop()
        verify(mockOperandStore, times(1)).restore(any())
    }

    @Test
    @DisplayName("Check execute when operation history is empty")
    fun executeWhenEmptyHistory() {
        val undoAction = UndoAction(mockOperandStore, mockOperationHistory)

        undoAction.execute()

        verify(mockOperationHistory, times(1)).pop()
    }
}
