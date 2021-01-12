package calcy.store

import calcy.Action
import java.math.BigDecimal
import java.util.Stack

class OperationHistory {
    private val operationStack = Stack<Operation>()

    fun push(operation: Operation) {
        operationStack.push(operation)
    }

    fun pop(): Operation? {
        if (operationStack.empty()) return null
        return operationStack.pop()
    }
}

data class Operation(val action: Action, val state: List<BigDecimal>)
