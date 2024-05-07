import dev.whyoleg.cryptography.*
import dev.whyoleg.cryptography.algorithms.digest.*

fun main() {
    val digest =
        CryptographyProvider.Default
            .get(SHA256)
            .hasher()
            .hashBlocking("Hello World".encodeToByteArray())
            .let(::printHexBinary)

    println(digest)
}

private const val hexCode = "0123456789ABCDEF"

private fun printHexBinary(data: ByteArray): String {
    val r = StringBuilder(data.size * 2)
    for (b in data) {
        r.append(hexCode[b.toInt() shr 4 and 0xF])
        r.append(hexCode[b.toInt() and 0xF])
    }
    return r.toString().lowercase()
}
