package binarytree

class BSTIterative<T : Comparable<T>> {
    private var root: BTNode<T>? = null

    /**
     * Inserts a new node into the binary search tree.
     *
     * @param node the node to insert, or null to do nothing
     */
    fun insert(node: BTNode<T>?) {
        node ?: return
        var current = root
        var parent: BTNode<T>? = null
        // Traverse to find the insertion point
        while (current != null) {
            parent = current
            current = if (node.value < current.value) {
                current.left
            } else {
                current.right
            }
        }
        // Insert the new node
        when {
            parent == null -> this@BSTIterative.root = node
            node.value < parent.value -> parent.left = node
            else -> parent.right = node
        }
    }

    fun delete(value: T) {
        root ?: return
        var parent: BTNode<T>? = null
        var current = root
        while (current != null && current.value != value) {
            parent = current
            current = if (value < current.value) {
                current.left
            } else {
                current.right
            }
        }
        if (current == null) {
            println("Node with value $value not found in the tree.")
            return
        }
        if (current.left == null || current.right == null) {
            // node has one or zero children
            val replacement: BTNode<T>? = when {
                current.isLeaf -> null
                current.left == null -> current.right
                else -> current.left
            }
            if (parent == null) {
                this@BSTIterative.root = replacement
            } else {
                if (parent.left?.value == current.value) {
                    parent.left = replacement
                } else {
                    parent.right = replacement
                }
            }
        } else {
            // node has two children
            var successorParent = current
            var successor = current.right
            while (successor?.left != null) {
                successorParent = successor
                successor = successor.left!!
            }
            current.value = successor!!.value
            if (successorParent?.left == successor) {
                successorParent.left = successor.right
            } else {
                // special case: successor is the right child of the parent
                successorParent?.right = successor.right
            }
        }
    }

    fun traverse(traversalType: BTTraversalType): List<T> {
        return when (traversalType) {
            BTTraversalType.PreOrder -> {
                preOrderTraversal(this.root)
            }

            BTTraversalType.PostOrder -> {
                postOrderTraversal(this.root)
            }

            BTTraversalType.InOrder -> {
                inOrderTraversal(this.root)
            }

            BTTraversalType.LevelOrder -> {
                levelOrderTraversals(this.root)
            }
        }
    }

    /**
     * Performs a pre-order traversal of the binary search tree.
     *
     * In a pre-order traversal, the current node is processed first,
     * followed by the left subtree, and then the right subtree.
     *
     * @param root the root of the subtree to traverse
     */
    private fun preOrderTraversal(root: BTNode<T>?): List<T> {
        root ?: return emptyList<T>()
        val result = mutableListOf<T>()
        val stack = ArrayDeque<BTNode<T>>()
        stack.addLast(root)
        while (stack.isNotEmpty()) {
            val currentNode = stack.removeLast()
            result.add(currentNode.value)
            currentNode.right?.let {
                stack.addLast(it)
            }
            currentNode.left?.let {
                stack.addLast(it)
            }
        }
        return result
    }

    /**
     * Performs a post-order traversal of the binary search tree.
     *
     * In a post-order traversal, the left subtree is processed first,
     * followed by the right subtree, and then the current node.
     *
     * @param root the root of the subtree to traverse
     */

    private fun postOrderTraversal(root: BTNode<T>?): List<T> {
        root ?: return emptyList<T>()
        val result = ArrayDeque<T>()
        val stack = ArrayDeque<BTNode<T>>()
        stack.addLast(root)
        while (stack.isNotEmpty()) {
            val currentNode = stack.removeLast()
            result.addFirst(currentNode.value)
            currentNode.left?.let {
                stack.addLast(it)
            }
            currentNode.right?.let {
                stack.addLast(it)
            }
        }
        return result
    }

    /**
     * Performs an in-order traversal of the binary search tree.
     *
     * In an in-order traversal, the left subtree is processed first,
     * followed by the current node, and then the right subtree.
     *
     * @param root the root of the subtree to traverse
     */
    private fun inOrderTraversal(root: BTNode<T>?): List<T> {
        root ?: return emptyList()
        val result = ArrayDeque<T>()
        val stack = ArrayDeque<BTNode<T>>()
        var current: BTNode<T>? = root
        while (stack.isNotEmpty() || current != null) {
            while (current != null) {
                stack.addLast(current)
                current = current.left
            }
            current = stack.removeLast()
            result.addLast(current.value)
            current = current.right
        }
        return result
    }

    /**  level order traversals of the binary search tree
     *   @param root the root of the subtree to traverse
     */
    private fun levelOrderTraversals(root: BTNode<T>?): List<T> {
        root ?: return emptyList()
        val stack = ArrayDeque<BTNode<T>>()
        stack.add(root)
        val result = ArrayDeque<T>()
        while (stack.isNotEmpty()) {
            val current = stack.removeFirst()
            result.addLast(current.value)
            current.left?.let { stack.addLast(it) }
            current.right?.let { stack.addLast(it) }
        }
        return result.toList()
    }

}