package calcy.action

import calcy.Action
import calcy.store.OperandStore
import calcy.store.Operation
import calcy.store.OperationHistory
import java.math.BigDecimal

class PushAction(
    private val operandStore: OperandStore,
    private val operationHistory: OperationHistory,
    private val value: BigDecimal
    ) : Action {
    override fun getIdentifier() = "push"

    override fun execute() {
        val preState = operandStore.getState()
        operandStore.enter(value)
        operationHistory.push(Operation(this, preState))
    }
}
