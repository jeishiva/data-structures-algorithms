package binarytree

class BTNode<T : Comparable<T>>(
    var value: T,
    var left: BTNode<T>? = null,
    var right: BTNode<T>? = null
) {
    val isLeaf: Boolean get() = left == null && right == null
}
