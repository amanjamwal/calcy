package calcy.action

import calcy.InsufficientParametersException
import calcy.store.OperandStore
import calcy.store.OperationHistory
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import java.math.BigDecimal

internal class SqrtActionTest {

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
        val sqrtAction = SqrtAction(mockOperandStore, mockOperationHistory)
        assertEquals("sqrt", sqrtAction.getIdentifier())
    }

    @Test
    @DisplayName("Check execute for happy path")
    fun executeHappyPath() {
        val value = 5
        val sqrtAction = SqrtAction(mockOperandStore, mockOperationHistory)
        Mockito.`when`(mockOperandStore.size()).thenReturn(1)
        Mockito.`when`(mockOperandStore.top())
            .thenReturn(BigDecimal(value * value))
        Mockito.`when`(mockOperandStore.remove())
            .thenReturn(BigDecimal(value * value))

        sqrtAction.execute()

        verify(mockOperandStore, times(1)).enter(bigDecimalCaptor.capture())
        assertEquals(value, bigDecimalCaptor.firstValue.toInt())

        verify(mockOperationHistory, times(1)).push(any())
    }

    @Test
    @DisplayName("Check execute when store doesn't have enough arguments")
    fun executeInsufficientArguments() {
        val sqrtAction = SqrtAction(mockOperandStore, mockOperationHistory)
        Mockito.`when`(mockOperandStore.size()).thenReturn(0)

        assertThrows<InsufficientParametersException> { sqrtAction.execute() }
    }
}
