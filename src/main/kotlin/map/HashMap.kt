package map

interface IMap<K, V> {
    fun put(key: K, value: V)

    fun get(key: K): V?

    fun remove(key: K): Boolean

    fun contains(key: K): Boolean

    fun size(): Int

    fun isEmpty(): Boolean
}

class MapEntry<K, V>(val key: K, val value: V, var isDeleted: Boolean)

class HashMap<K, V> : IMap<K, V> {
    private var capacity = 4
    private val threshold = 0.8f
    private var size = 0
    private var dataArray: Array<MapEntry<K, V>?> = arrayOfNulls<MapEntry<K, V>?>(capacity)

    override fun put(key: K, value: V) {
        checkCapacity()
        val index = getFreeSlotForKey(key)
        println("index $index for value : $value")
        val existingEntry = dataArray[index]
        dataArray[index] = MapEntry(key, value, false)
        // increase size only if existing entry is null or
        // deleted one, avoids increasing size when updating same key
        if (existingEntry == null || existingEntry.isDeleted) {
            size++
        }
    }

    private fun getFreeSlotForKey(key: K): Int {
        var index = keyToSlot(key)
        var deletedIndex = -1
        var result = -1
        repeat(capacity) {
            val slot = dataArray[index]
            if (deletedIndex == -1 && slot?.isDeleted == true ) {
                deletedIndex = index
            }
            if (slot == null || slot.key == key) {
                result = index
                return@repeat
            }
            index = (index + 1) % capacity
        }
        if (result != -1) {
            return result
        }
        if (deletedIndex != -1) {
            return deletedIndex
        }
        throw IllegalStateException("table is full, slot not found")
    }

    private fun searchSlotForKey(key: K): Int {
        var index = keyToSlot(key)
        repeat(capacity) {
            val slot = dataArray[index]
            if (slot?.key == key && slot?.isDeleted == false) {
                return index
            }
            index = (index + 1) % capacity
        }
        return -1
    }

    override fun contains(key: K): Boolean {
        val index = searchSlotForKey(key)
        return index != -1
    }

    private fun keyToSlot(key: K): Int {
        return (key.hashCode() and 0x7FFFFFFF) % capacity
    }

    private fun checkCapacity() {
        if (size <= capacity * threshold) {
            return
        }
        capacity *= 2
        size = 0
        val tempArray = dataArray
        dataArray = arrayOfNulls(capacity)
        tempArray.filterNotNull().onEach {
            put(it.key, it.value)
        }
    }

    override fun get(key: K): V? {
        val index = searchSlotForKey(key)
        if (index == -1) {
            return null
        }
        return dataArray[index]?.value
    }

    override fun remove(key: K): Boolean {
        val index = searchSlotForKey(key)
        if (index == -1) {
            return false
        }
        dataArray[index]?.isDeleted = true
        size--
        return true
    }

    override fun size(): Int = size

    override fun isEmpty(): Boolean = size == 0

}