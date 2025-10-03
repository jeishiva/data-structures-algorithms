package binarysearch.problems


fun kthSmallest(matrix: Array<IntArray>, k: Int): Int {
    val n = matrix.size
    var left = matrix[0][0]
    var right = matrix[n - 1][n - 1]
    while (left < right) {
        val mid = left + (right - left) / 2
        val count = countItems(matrix, mid)
        if (count < k) {
            left = mid + 1
        } else {
            right = mid
        }
    }
    return left
}

fun countItems(matrix: Array<IntArray>, mid: Int): Int {
    println("mid : $mid")
    var row = matrix.size - 1
    var column = 0
    var count = 0
    while (row >= 0 && column < matrix.size) {
        if (matrix[row][column] <= mid) {
            count += (row + 1)
            column++
        } else {
            row--
        }
    }
    return count
}