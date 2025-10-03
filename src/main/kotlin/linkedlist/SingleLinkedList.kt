package linkedlist


data class SingleLinkedListNode(val value: Int, var next: SingleLinkedListNode? = null)

class SingleLinkedList {

    private var head: SingleLinkedListNode? = null

    fun insertAtHead(value: Int) {
        val node = value.toSingleLinkedListNode()
        node.next = head
        head = node
    }

    fun insertAtTail(value: Int) {
        if (head == null) {
            insertAtHead(value)
            return
        }
        var current: SingleLinkedListNode? = head
        while (current?.next != null) {
            current = current.next
        }
        current?.next = value.toSingleLinkedListNode()
    }

    fun insertAtPosition(value: Int, position: Int) {
        val dummy = SingleLinkedListNode(-1)
        dummy.next = head
        var currentPosition = 0
        var current: SingleLinkedListNode? = dummy
        while (current?.next != null && position != currentPosition) {
            current = current.next
            currentPosition++
        }
        val temp = current?.next
        val newNode = value.toSingleLinkedListNode()
        current?.next = newNode
        newNode.next = temp
        if (position == 0) {
            head = newNode
        }
    }

    fun deleteByValue(value: Int) {
        val dummy = SingleLinkedListNode(-1)
        dummy.next = head
        var current: SingleLinkedListNode? = dummy
        while (current?.next != null && current.next!!.value != value) {
            current = current.next
        }
        if (current?.next?.value == value) {
            current.next = current.next?.next
        }
        head = dummy.next
    }

    fun deleteAtPosition(position: Int) {
        val dummy = SingleLinkedListNode(-1)
        dummy.next = head
        var current: SingleLinkedListNode? = dummy
        var currentPosition = 0
        while (current?.next != null && currentPosition != position) {
            current = current.next
            currentPosition++
        }
        if (currentPosition == position) {
            current?.next = current?.next?.next
            head = dummy.next
        } else {
            println("no such position exist")
        }
    }

    fun search(value: Int): Boolean {
        val dummy = SingleLinkedListNode(-1)
        dummy.next = head
        var current: SingleLinkedListNode? = dummy
        while (current?.next != null && current.next!!.value != value) {
            current = current.next
        }
        return current?.next?.value == value
    }

    fun reverse() {
        var prev: SingleLinkedListNode? = null
        var current: SingleLinkedListNode? = head
        while (current != null) {
            val temp = current.next
            current.next = prev
            prev = current
            current = temp
        }
        head = prev
    }

    fun findMiddle(): SingleLinkedListNode? {
        var fast: SingleLinkedListNode? = head
        var slow: SingleLinkedListNode? = head
        while (fast?.next != null) {
            fast = fast.next!!.next
            slow = slow?.next
        }
        return slow
    }

    fun hasCycle(): Boolean {
        var fast: SingleLinkedListNode? = head
        var slow: SingleLinkedListNode? = head
        while (fast?.next != null) {
            fast = fast.next!!.next
            slow = slow?.next
            if (fast == slow) {
                return true
            }
        }
        return false
    }

    fun printLinkedList() {
        var current = head
        while (current != null) {
            print("${current.value} -> ")
            current = current.next
        }
    }

}


fun Int.toSingleLinkedListNode() = SingleLinkedListNode(this)