class Configuration {

    private val items:MutableMap<Id,Item> = mutableMapOf()
    fun addItem(item:Item) {
        items[item.id] = item
    }
    operator fun get(id:Id) = items.getValue(id)
    override fun toString() = items.values.joinToString("\n") { it.toString() }

    /*
    companion object {
        private val items:MutableMap<Id,Item> = mutableMapOf()
        fun addItem(item:Item) {
            items[item.id] = item
        }
        operator fun get(id:Id) = items.getValue(id)
        override fun toString() = items.values.joinToString("\n") { it.toString() }

        fun reset() {
            items.clear()
        }
    }
     */
}