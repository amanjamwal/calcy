package calcy.action

import calcy.Action
import calcy.store.OperandStore
import calcy.store.Operation
import calcy.store.OperationHistory

class ClearAction(private val operandStore: OperandStore, private val operationHistory: OperationHistory) : Action {
    override fun getIdentifier() = "clear"

    override fun execute() {
        val values = operandStore.getState()
        if (values.isEmpty()) return

        operandStore.clear()

        operationHistory.push(Operation(this, values))
    }
}
