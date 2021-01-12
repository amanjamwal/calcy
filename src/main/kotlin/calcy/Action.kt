package calcy

interface Action {
    fun getIdentifier(): String
    fun execute(): Unit
}
