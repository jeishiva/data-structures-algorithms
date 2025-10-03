import binarytree.BSTRecursive
import binarytree.BTNode
import binarytree.BTTraversalType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**      BST test data
 *          50
 *        /  \
 *      30    70
 *     /  \   / \
 *   20   40 60
 *  /
 * 10
 *
 */
class BSTRecursiveTest {
    private lateinit var bst: BSTRecursive<Int>
    private val inputValues = listOf(50, 30, 70, 20, 40, 60, 10)

    @BeforeEach
    fun setUp() {
        bst = BSTRecursive()
        inputValues.forEach { bst.insert(BTNode(it)) }
    }

    @Test
    fun `in-order traversal should return sorted list`() {
        val expected = inputValues.sorted()
        val result = bst.traverse(BTTraversalType.InOrder)
        assertEquals(expected, result)
    }

    @Test
    fun `pre-order traversal should return correct node order`() {
        val expected = listOf(50, 30, 20, 10, 40, 70, 60)
        val result = bst.traverse(BTTraversalType.PreOrder)
        assertEquals(expected, result)
    }

    @Test
    fun `post-order traversal should return correct node order`() {
        val expected = listOf(10, 20, 40, 30, 60, 70, 50)
        val result = bst.traverse(BTTraversalType.PostOrder)
        assertEquals(expected, result)
    }

    @Test
    fun `delete leaf node should remove it from tree`() {
        bst.delete(10)
        val expected = listOf(20, 30, 40, 50, 60, 70)
        assertEquals(expected, bst.traverse(BTTraversalType.InOrder))
    }

    @Test
    fun `delete node with one child should remove and restructure correctly`() {
        bst.delete(20)  // 20 has one left child 10
        val expected = listOf(10, 30, 40, 50, 60, 70)
        assertEquals(expected, bst.traverse(BTTraversalType.InOrder))
    }

    @Test
    fun `delete node with two children should restructure correctly`() {
        bst.delete(30)  // 30 has 20 and 40 as children
        val expected = listOf(10, 20, 40, 50, 60, 70)
        assertEquals(expected, bst.traverse(BTTraversalType.InOrder))
    }

    @Test
    fun `delete root should restructure tree correctly`() {
        bst.delete(50)
        val expected = listOf(10, 20, 30, 40, 60, 70)
        assertEquals(expected, bst.traverse(BTTraversalType.InOrder))
    }
}
