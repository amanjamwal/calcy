package calcy

import calcy.action.*
import calcy.store.OperandStore
import calcy.store.OperationHistory
import org.slf4j.LoggerFactory
import java.util.*

fun main() {
    val log = LoggerFactory.getLogger("calcy.Main")
    val operandStore = OperandStore()
    val operationHistory = OperationHistory()

    val actionsList = listOf(
        AddAction(operandStore, operationHistory),
        SubtractAction(operandStore, operationHistory),
        MultiplyAction(operandStore, operationHistory),
        DivideAction(operandStore, operationHistory),
        SqrtAction(operandStore, operationHistory),
        ClearAction(operandStore, operationHistory),
        UndoAction(operandStore, operationHistory)
    )
    val actionRegistry = ActionRegistry(actionsList)

    val scanner = Scanner(System.`in`)
    var keepProcessing = true
    while (keepProcessing) {
        val nextLine = scanner.nextLine()
        val elements = nextLine.toLowerCase().trim().split("\\s+".toRegex())
        for ((pos, element) in elements.withIndex()) {
            if (element == "exit") {
                keepProcessing = false
                break
            }

            val value = element.toBigDecimalOrNull()
            when {
                value != null -> {
                    PushAction(operandStore, operationHistory, value).execute()
                }
                actionRegistry.contains(element) -> {
                    val action = actionRegistry.get(element)!!
                    try {
                        action.execute()
                    } catch (exception: InsufficientParametersException) {
                        println("operator $element (position: $pos): insufficient parameters")
                    } catch (exception: IllegalParametersException) {
                        println("operator $element (position: $pos): illegal parameters")
                    }
                }
                else -> {
                    log.error("Unexpected input")
                }
            }
        }

        println(operandStore.toString())
    }
}
