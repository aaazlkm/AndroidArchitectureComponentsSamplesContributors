package hoge.hogehoge.infra.api.github.model.header

import org.junit.Assert.assertEquals
import org.junit.Test

class RawLinkHeaderTest {

    @Test
    fun from() {
        val testCase =
            "<https://api.github.com/repositories/90792131/contributors?anon=1&page=2>; rel=\"next\", " +
                    "<https://api.github.com/repositories/90792131/contributors?anon=1&page=2>; rel=\"last\", " +
                    "<https://api.github.com/repositories/90792131/contributors?anon=1&page=6>; rel=\"first\", " +
                    "<https://api.github.com/repositories/90792131/contributors?anon=1&page=4>; rel=\"prev\""

        val result = RawLinkHeader.from(testCase)
        val expected = RawLinkHeader(
            nextPage = 2,
            lastPage = 2,
            firstPage = 6,
            prevPage = 4
        )
        assertEquals(expected, result)
    }
}
