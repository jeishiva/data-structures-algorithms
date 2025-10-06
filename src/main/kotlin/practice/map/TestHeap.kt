package practice.map

import com.jeishiva.utils.swap
import java.lang.IllegalStateException


class HeapNode(val data: Int)

class TestMinHeap(private val initialCapacity: Int) {

    private var heap: Array<HeapNode?> = Array(size = initialCapacity) { null }

    var size: Int = 0
    private var capacity = initialCapacity
    private val threshold: Float = 0.75f

    private fun getParentIndex(childIndex: Int): Int = (childIndex - 1) / 2
    private fun getLeftChildIndex(parentIndex: Int): Int = (parentIndex * 2) + 1
    private fun getRightChildIndex(parentIndex: Int): Int = (parentIndex * 2) + 2

    private fun hasParent(childIndex: Int) = getParentIndex(childIndex) >= 0
    private fun hasLeftChild(parentIndex: Int) = getLeftChildIndex(parentIndex) < size
    private fun hasRightChild(parentIndex: Int) = getRightChildIndex(parentIndex) < size
    private fun hasChild(parentIndex: Int) = hasLeftChild(parentIndex) || hasRightChild(parentIndex)


    fun push(item: Int) {
        ensureCapacity()
        val node = HeapNode(data = item)
        heap[size] = node
        size++
        heapifyUp()
    }

    private fun ensureCapacity() {
        if (size / capacity >= threshold) {
            val currentData = heap
            capacity *= 2
            val newData = Array<HeapNode?>(capacity) { null }
            currentData.copyInto(newData, startIndex = 0)
            heap = currentData
        }
    }

    fun peek(): Int {
        ensureHeapNotEmpty()
        return heap[size]!!.data
    }

    fun poll(): Int {
        ensureHeapNotEmpty()
        val node = heap[0]
        heap[0] = null
        heap[0] = heap[size - 1]
        size--
        heapifyDown()
        return node!!.data
    }

    private fun ensureHeapNotEmpty() {
        if (size == 0) {
            throw IllegalStateException("heap is empty, cannot perform this operation")
        }
    }

    private fun heapifyDown() {
        var parentIndex = 0
        while (hasChild(parentIndex)) {
            var leftChildIndex = getLeftChildIndex(parentIndex)
            var rightChildIndex = getRightChildIndex(parentIndex)
            var minIndex = leftChildIndex
            if (hasRightChild(parentIndex) && heap[rightChildIndex]!!.data < heap[leftChildIndex]!!.data) {
                minIndex = rightChildIndex
            }
            heap.swap(minIndex, parentIndex)
            parentIndex = minIndex
        }
    }

    private fun heapifyUp() {
        var childIndex = size - 1
        while (hasParent(childIndex) && (heap[getParentIndex(childIndex)]!!.data) > (heap[childIndex]!!.data)) {
            heap.swap(getParentIndex(childIndex), childIndex)
            childIndex = getParentIndex(childIndex)
        }
    }

    fun isEmpty() = size == 0
}