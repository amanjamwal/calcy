package calcy.action

import calcy.Action
import calcy.IllegalParametersException
import calcy.InsufficientParametersException
import calcy.store.OperandStore
import calcy.store.Operation
import calcy.store.OperationHistory
import java.math.BigDecimal
import java.math.MathContext.DECIMAL64

class SqrtAction(private val operandStore: OperandStore, private val operationHistory: OperationHistory) : Action {
    override fun getIdentifier() = "sqrt"

    override fun execute() {
        if (operandStore.size() < 1) throw InsufficientParametersException("Required one params")
        if (operandStore.top() < BigDecimal.ZERO) throw IllegalParametersException("Required non negative param")

        val preState = operandStore.getState()

        val operand = operandStore.remove()
        operandStore.enter(operand.sqrt(DECIMAL64))

        operationHistory.push(Operation(this, preState))
    }
}
