package calcy.action

import calcy.Action
import calcy.InsufficientParametersException
import calcy.store.OperandStore
import calcy.store.Operation
import calcy.store.OperationHistory

class DivideAction(private val operandStore: OperandStore, private val operationHistory: OperationHistory) : Action {
    override fun getIdentifier() = "/"

    override fun execute() {
        if (operandStore.size() < 2) throw InsufficientParametersException("Required two params")

        val preState = operandStore.getState()

        val secondOperand = operandStore.remove()
        val firstOperand = operandStore.remove()
        operandStore.enter(firstOperand / secondOperand)

        operationHistory.push(Operation(this, preState))
    }
}
