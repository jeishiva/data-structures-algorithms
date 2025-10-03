

fun main() {
    val lruCache = LRUCache<String, String>(3)
    lruCache.apply {
        put("1", "one")
        put("2", "two")
        put("3", "three")
    }
    lruCache.get("1")
    lruCache.put("4", "four")
}


class Node<K, V>(
    val key: K,
    var value: V?,
    var next: Node<K, V>? = null,
    var prev: Node<K, V>? = null,
)

class LRUCache<K, V>(private val maxCapacity: Int) {
    private val map = mutableMapOf<K, Node<K, V>>()

    // least recently used
    private var head: Node<K, V>? = null

    // most recently used
    private var tail: Node<K, V>? = null

    fun get(key: K): V? {
        val node = map[key] ?: return null
        moveToTail(node)
        return node.value
    }

    fun put(key: K, value: V?) {
        val existing = map[key]
        if (existing != null) {
            println("node exists in the map, update and move to tail")
            existing.value = value
            moveToTail(existing)
        } else {
            val node = Node(key, value)
            map[key] = node
            addNode(node)
        }
    }

    private fun addNode(node: Node<K, V>) {
        if (head == null) {
            head = node
            tail = node
            return
        }
        // update tail
        node.prev = tail
        tail?.next = node
        tail = node

        // tail will be updated in moveToTail
        if (map.size > maxCapacity) {
            removeNode(head!!)
        }
    }

    private fun moveToTail(node: Node<K, V>) {
        if (tail == node) {
            println("no changes required as node $node already the tail")
            return
        }
        // update head
        if (node == head) {
            head = node.next
        }

        // unlink the node
        node.prev?.next = node.next
        node.next?.prev = node.prev

        // connect at end
        tail?.next = node
        node.prev = tail
        node.next = null

        // update tail
        tail = node

    }

    private fun removeNode(node: Node<K, V>) {
        map[node.key]?.let { evictNode ->
            println("evicting node ${evictNode.value}")
            map.remove(node.key)
            // unlink the node
            evictNode.prev?.next = evictNode.next
            evictNode.next?.prev = evictNode.prev
            if (evictNode == head) {
                head = evictNode.next
            }
            if (evictNode == tail) {
                tail = evictNode.prev
            }
        }
    }
}
