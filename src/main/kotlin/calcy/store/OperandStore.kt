package calcy.store

import calcy.LimitReachedException
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

private const val STORE_DECIMAL_PRECISION = 15
private const val VIEW_DECIMAL_PRECISION = 10
private val ROUNDING_MODE = RoundingMode.HALF_EVEN

class OperandStore(private val maxLimit: Int = Int.MAX_VALUE) {
    private val operandStack = Stack<BigDecimal>()

    fun size(): Int = operandStack.size

    fun enter(value: BigDecimal) {
        if (operandStack.size >= maxLimit) throw LimitReachedException("Store limit exceeded")
        val valueToBeStored = value.setScale(STORE_DECIMAL_PRECISION, ROUNDING_MODE)
        operandStack.push(valueToBeStored)
    }

    fun top(): BigDecimal {
        return operandStack.peek()
    }

    fun remove(): BigDecimal {
        return operandStack.pop()
    }

    fun clear() {
        operandStack.clear()
    }

    fun restore(state: List<BigDecimal>) {
        operandStack.clear()
        state.forEach { element -> operandStack.push(element) }
    }

    fun getState(): List<BigDecimal> {
        return operandStack.toList()
    }

    override fun toString(): String {
        val values = getState()

        val sb = StringBuilder()
        sb.append("Stack:")
        for (value in values) {
            val valueToBeViewed = value.setScale(VIEW_DECIMAL_PRECISION, ROUNDING_MODE)
            sb.append(" ").append(valueToBeViewed.stripTrailingZeros().toPlainString())
        }
        return sb.toString()
    }
}
