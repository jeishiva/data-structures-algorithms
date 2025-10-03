package binarytree

/**
 *  binary search tree insertion, deletion, traversals
 *  by recursive (v1), (v2) -> will be iterative
 */
class BSTRecursive<T : Comparable<T>> {
    private var root: BTNode<T>? = null

    /**
     * Inserts a node into the binary search tree.
     *
     * @param node the node to insert into the tree
     */
    fun insert(node: BTNode<T>?) {
        node ?: return
        root = insertRecursive(node = node, root = root)
    }

    /**
     * Recursive helper function to insert a node into the tree.
     *
     * @param node: the node to insert
     * @param root: the current root of the tree
     * @return the new root of the tree
     */
    private fun insertRecursive(
        node: BTNode<T>,
        root: BTNode<T>?
    ): BTNode<T> {
        root ?: return node
        if (node.value < root.value) {
            root.left = insertRecursive(node, root.left)
        } else {
            root.right = insertRecursive(node, root.right)
        }
        return root
    }

    /**
     * Delete a node from the tree.
     *
     * @param value: the value of the node to delete
     */
    fun delete(value: T) {
        root = deleteRecursive(value, root)
    }

    /**
     * Recursively deletes a node with the specified value from the binary search tree.
     *
     * @param value the value of the node to delete
     * @param node the current node being examined
     * @return the new subtree root after deletion
     */
    private fun deleteRecursive(value: T, node: BTNode<T>?): BTNode<T>? {
        node ?: return null
        return when {
            node.value == value -> when {
                node.isLeaf -> null
                node.left == null -> node.right
                node.right == null -> node.left
                else -> {
                    val successor = findSuccessor(node.right)!!
                    node.value = successor.value
                    node.right = deleteRecursive(successor.value, node.right)
                    node
                }
            }

            value > node.value -> {
                node.right = deleteRecursive(value, node.right)
                node
            }

            else -> {
                node.left = deleteRecursive(value, node.left)
                node
            }
        }
    }

    /**
     * Finds the in-order successor of a node in a binary search tree.
     *
     * The in-order successor of a node is the node with the smallest value greater than the
     * given node's value. It is the node which is one position to the right of the given node in
     * the in-order traversal of the tree.
     *
     * @param node the node whose in-order successor is to be found
     * @return the in-order successor of the given node
     */
    private fun findSuccessor(node: BTNode<T>?): BTNode<T>? {
        node ?: return null
        var current = node
        while (current?.left != null) {
            current = current.left
        }
        return current
    }

    /**
     * Traverses the binary search tree using the specified traversal type.
     *
     * This function delegates the traversal operation to the appropriate helper function
     * based on the specified traversal type: pre-order, post-order, or in-order.
     *
     * @param traversalType the type of traversal to perform on the tree
     *                      (PreOrder, PostOrder, or InOrder)
     */
    fun traverse(traversalType: BTTraversalType): List<T> {
        val result = mutableListOf<T>()
        when (traversalType) {
            BTTraversalType.PreOrder -> {
                preOrderTraversal(this.root, result)
            }

            BTTraversalType.PostOrder -> {
                postOrderTraversal(this.root, result)
            }

            BTTraversalType.InOrder -> {
                inOrderTraversal(this.root, result)
            }

            BTTraversalType.LevelOrder -> {
                return levelTraversal(this.root)
            }
        }
        return result
    }

    /**
     * Performs a pre-order traversal of the binary search tree.
     *
     * In a pre-order traversal, the current node is processed first,
     * followed by the left subtree, and then the right subtree.
     *
     * @param root the root of the subtree to traverse
     */
    private fun preOrderTraversal(root: BTNode<T>?, result: MutableList<T>) {
        root ?: return
        result.add(root.value)
        preOrderTraversal(root.left, result)
        preOrderTraversal(root.right, result)
    }

    /**
     * Performs a post-order traversal of the binary search tree.
     *
     * In a post-order traversal, the left subtree is processed first,
     * followed by the right subtree, and then the current node.
     *
     * @param root the root of the subtree to traverse
     */
    private fun postOrderTraversal(root: BTNode<T>?, result: MutableList<T>) {
        root ?: return
        postOrderTraversal(root.left, result)
        postOrderTraversal(root.right, result)
        result.add(root.value)
    }

    /**
     * Performs an in-order traversal of the binary search tree.
     *
     * In an in-order traversal, the left subtree is processed first,
     * followed by the current node, and then the right subtree.
     *
     * @param root the root of the subtree to traverse
     */
    private fun inOrderTraversal(root: BTNode<T>?, result: MutableList<T>) {
        root ?: return
        inOrderTraversal(root.left, result)
        result.add(root.value)
        inOrderTraversal(root.right, result)
    }


    private fun levelTraversal(root: BTNode<T>?): List<T> {
        root ?: return emptyList()
        val level = ArrayDeque<BTNode<T>>()
        level.addLast(root)
        return levelRecursiveHelper(level)
    }

    private fun levelRecursiveHelper(
        currentLevel: ArrayDeque<BTNode<T>>,
    ): List<T> {
        if (currentLevel.isEmpty()) {
            return emptyList()
        }
        val nextLevel = ArrayDeque<BTNode<T>>()
        val levelValues = mutableListOf<T>()
        while (currentLevel.isNotEmpty()) {
            val node = currentLevel.removeFirst()
            levelValues.add(node.value)
            node.left?.let {
                nextLevel.addLast(it)
            }
            node.right?.let {
                nextLevel.addLast(it)
            }
        }
        return levelValues + levelRecursiveHelper(nextLevel)
    }
}