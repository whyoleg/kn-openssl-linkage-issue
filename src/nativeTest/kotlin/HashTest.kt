import kotlin.test.*

class HashTest {

    @Test
    fun test() {
        val digest = sha256("Hello World")
        assertEquals("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e", digest)
    }
}
