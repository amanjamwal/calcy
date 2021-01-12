package calcy.action

import calcy.Action
import calcy.store.OperandStore
import calcy.store.OperationHistory

class UndoAction(private val operandStore: OperandStore, private val operationHistory: OperationHistory) : Action {
    override fun getIdentifier() = "undo"

    override fun execute() {
        val operation = operationHistory.pop() ?: return
        operandStore.restore(operation.state)
    }
}
