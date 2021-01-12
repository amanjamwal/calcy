package calcy.action

import calcy.store.OperandStore
import calcy.store.OperationHistory
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Captor
import org.mockito.Mock
import java.math.BigDecimal

internal class PushActionTest {

    @Mock
    private lateinit var mockOperandStore: OperandStore

    @Mock
    private lateinit var mockOperationHistory: OperationHistory

    @Captor
    private lateinit var bigDecimalCaptor: KArgumentCaptor<BigDecimal>

    @BeforeEach
    fun setUp() {
        mockOperandStore = mock()
        mockOperationHistory = mock()
        bigDecimalCaptor = argumentCaptor()
    }

    @Test
    @DisplayName("Check Identifier")
    fun getIdentifier() {
        val pushAction = PushAction(mockOperandStore, mockOperationHistory, BigDecimal.valueOf(10))
        assertEquals("push", pushAction.getIdentifier())
    }

    @Test
    @DisplayName("Check execute for happy path")
    fun executeHappyPath() {
        val anyValue = BigDecimal.valueOf(10)
        val pushAction = PushAction(mockOperandStore, mockOperationHistory, anyValue)

        pushAction.execute()

        verify(mockOperandStore, times(1)).enter(bigDecimalCaptor.capture())
        assertEquals(anyValue.toInt(), bigDecimalCaptor.firstValue.toInt())

        verify(mockOperationHistory, times(1)).push(any())
    }
}
