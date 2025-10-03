package practice.map

import java.lang.IllegalStateException

interface ITestMap<K, V> {

    fun put(key: K, value: V?)

    fun get(key: K): V?

    fun size(): Int

    fun remove(key: K): V?

    fun isEmpty(): Boolean

}

class MapEntry<K, V>(val key: K, var value: V?, var isDeleted: Boolean = false)

class TestHashMap<K, V> : ITestMap<K, V> {

    private var capacity: Int = 10
    private val threshold: Float = 0.75f
    private var size: Int = 0
    private var data = arrayOfNulls<MapEntry<K, V?>>(capacity)

    override fun put(key: K, value: V?) {
        ensureCapacity()
        val index = getFreeSlotForKey(key)
        val existingData = data[index]
        if (existingData == null || existingData.isDeleted) {
            data[index] = MapEntry(key, value)
            size++
        } else if (existingData.key == key) {
            existingData.value = value
        }
    }

    private fun getFreeSlotForKey(key: K): Int {
        var index = getBucketIndexForKey(key)
        var deletedIndex = -1
        var result = -1
        repeat(capacity) {
            val slot = data[index]
            if (slot == null || (slot.key == key && slot.isDeleted.not())) {
                result = index
                return@repeat
            }
            if (deletedIndex == -1 && slot.isDeleted) {
                deletedIndex = index
            }
            index = (index + 1) % capacity
        }
        if (result != -1) {
            return result
        }
        if (deletedIndex != -1) {
            return deletedIndex
        }
        throw IllegalStateException("table is full, no free slots")
    }

    private fun getBucketIndexForKey(key: K): Int {
        return (key.hashCode() and 0x7FFFFFFF) % capacity
    }

    private fun getSlotForKey(key: K): Int {
        var index = getBucketIndexForKey(key)
        repeat(capacity) {
            val slot = data[index]
            if (slot?.key == key && slot?.isDeleted == false) {
                return index
            }
            index = (index + 1) % capacity
        }
        return -1
    }

    private fun ensureCapacity() {
        if (size < capacity * threshold) {
            return
        }
        capacity *= 2
        size = 0
        val existingData = data
        data = arrayOfNulls(capacity)
        for (data in existingData.filterNotNull()) {
            put(data.key, data.value)
        }
    }

    override fun get(key: K): V? {
        val index = getSlotForKey(key)
        if (index != -1) {
            return data[index]?.value
        }
        return null
    }

    override fun size(): Int = size

    override fun remove(key: K): V? {
        val index = getSlotForKey(key)
        if (index != -1) {
            data[index]?.isDeleted = true
            size--
            return data[index]?.value
        }
        return null
    }

    override fun isEmpty(): Boolean = size == 0

}