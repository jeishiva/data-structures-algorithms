package binarysearch.problems

import com.jeishiva.binary_search.binarySearch

fun searchRotatedArray(nums: IntArray, target: Int): Int {
    if (nums.isEmpty()) {
        return -1
    }
    if (nums.first() == target) {
        return 0
    }
    val indexPair = if (nums.size == 1 || nums.first() <= nums.last()) {
        // not rotated
        Pair(0, nums.size - 1)
    } else {
        // rotated
        val pivot = findPivot(nums)
        if (target <= nums.last()) {
            Pair(pivot, nums.size - 1)
        } else {
            Pair(0, pivot)
        }
    }
    val index = binarySearch(
        sortedInput = nums,
        search = target,
        left = indexPair.first,
        right = indexPair.second
    )
    return index
}