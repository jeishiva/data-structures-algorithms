import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import practice.LRUCacheCore

class LRUCacheCoreTest {

    private lateinit var cache: LRUCacheCore<String, Int>

    @BeforeEach
    fun setUp() {
        cache = LRUCacheCore(3)
    }

    @Test
    fun testBasicPutGet() {
        cache.put("a", 1)
        cache.put("b", 2)

        assertEquals(1, cache.get("a"))
        assertEquals(2, cache.get("b"))
    }

    @Test
    fun testSizeAfterPut() {
        cache.put("a", 1)
        cache.put("b", 2)
        assertEquals(2, cache.size())
    }

    @Test
    fun testOverwriteExistingKey() {
        cache.put("a", 1)
        cache.put("a", 10)

        assertEquals(10, cache.get("a"))
        assertEquals(1, cache.size(), "Size should not increase when overwriting")
    }

    @Test
    fun testEvictionWhenCapacityExceeded() {
        cache.put("a", 1)
        cache.put("b", 2)
        cache.put("c", 3)
        cache.put("d", 4) // should evict "a"

        assertNull(cache.get("a"), "Key 'a' should be evicted")
        assertEquals(3, cache.size(), "Cache size should not exceed capacity")
    }

    @Test
    fun testRecencyUpdateOnGet() {
        cache.put("a", 1)
        cache.put("b", 2)
        cache.put("c", 3)

        // Access 'a' to make it most recent
        assertEquals(1, cache.get("a"))

        // Inserting new element should evict 'b' (the least recently used now)
        cache.put("d", 4)

        assertNull(cache.get("b"))
        assertEquals(1, cache.get("a"))
        assertEquals(3, cache.size())
    }

    @Test
    fun testNullValueStored() {
        cache.put("x", null)
        assertTrue(cache.size() <= 3)
        assertNull(cache.get("x"))
    }

    @Test
    fun testSingleElementCache() {
        val single = LRUCacheCore<String, Int>(1)
        single.put("x", 100)
        assertEquals(100, single.get("x"))

        single.put("y", 200)
        assertNull(single.get("x"), "x should be evicted in single-element cache")
        assertEquals(200, single.get("y"))
    }

    @Test
    fun testEvictionOrder() {
        cache.put("a", 1)
        cache.put("b", 2)
        cache.put("c", 3)
        // Access 'a' → order becomes b (LRU), c, a (MRU)
        cache.get("a")

        cache.put("d", 4) // should evict 'b'
        assertNull(cache.get("b"))
        assertEquals(1, cache.get("a"))
        assertEquals(3, cache.size())
    }

    @Test
    fun testIllegalCapacity() {
        val ex = assertThrows(IllegalStateException::class.java) {
            LRUCacheCore<String, Int>(0)
        }
        assertTrue(ex.message!!.contains("must be > 0"))
    }
}
