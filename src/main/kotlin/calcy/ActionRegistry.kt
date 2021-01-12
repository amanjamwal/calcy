package calcy

class ActionRegistry(actionsList: List<Action>) {
    private val actionMap: Map<String, Action> = actionsList.map { it.getIdentifier() to it }.toMap()

    fun contains(actionId: String): Boolean {
        return actionMap.contains(actionId)
    }

    fun get(actionId: String): Action? {
        return actionMap[actionId]
    }
}
