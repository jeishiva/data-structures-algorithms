package practice

import java.lang.IllegalStateException

class CacheNode<K, V>(
    val key: K,
    var value: V?,
    var prev: CacheNode<K, V?>? = null,
    var next: CacheNode<K, V?>? = null,
)

interface LRUCache<K, V> {
    fun put(key: K, value: V?)
    fun get(key: K): V?
    fun size(): Int
}

class LRUCacheCore<K, V>(private val maxCacheSize: Int) : LRUCache<K, V?> {
    private val nodeMap = mutableMapOf<K, CacheNode<K, V?>>()
    private var head: CacheNode<K, V?>? = null
    private var tail: CacheNode<K, V?>? = null

    init {
        if (maxCacheSize <= 0) {
            throw IllegalStateException("cache size must be > 0")
        }
    }

    override fun put(key: K, value: V?) {
        val existingNode: CacheNode<K, V?>? = nodeMap[key]
        val node = if (existingNode == null) {
            val newNode: CacheNode<K, V?> = CacheNode(key, value = value)
            nodeMap[key] = newNode
            newNode
        } else {
            existingNode.value = value
            existingNode
        }
        if (head == null) {
            head = node
            tail = node
        } else {
            moveToTail(node)
        }
        evictIfCapacityExceeded()
    }

    private fun evictIfCapacityExceeded() {
        if (nodeMap.size <= maxCacheSize || head == null) {
            return
        }
        val leastUsedNode: CacheNode<K, V?> = head!!

        // update head
        head = head?.next
        head?.prev = null
        // update tail
        if (leastUsedNode == tail) {
            val newTail = tail?.prev
            newTail?.next = null
            tail?.next = newTail
            newTail?.prev = tail
            tail = newTail
        }
        nodeMap.remove(leastUsedNode.key)
        unlinkNode(leastUsedNode)
        println("removed ${leastUsedNode.key}: ${leastUsedNode.value}")

    }

    private fun unlinkNode(node: CacheNode<K, V?>) {
        // unlink node
        val prev = node.prev
        val next = node.next
        prev?.next = next
        next?.prev = prev
        node.prev = null
        node.next = null
    }

    override fun get(key: K): V? {
        val node: CacheNode<K, V?> = nodeMap[key] ?: return null
        moveToTail(node)
        return node.value
    }

    private fun moveToTail(node: CacheNode<K, V?>) {
        if (node == tail) {
            println("node ${node.key} : ${node.value} already in tail")
            return
        }

        // update head
        if (head == node) {
            val newHead = head?.next
            newHead?.prev = null
            head = newHead
        }

        // unlink node
        unlinkNode(node)

        // add at end and make it tail
        tail?.next = node
        node.prev = tail
        node.next = null
        tail = node
    }

    override fun size(): Int = nodeMap.size


}