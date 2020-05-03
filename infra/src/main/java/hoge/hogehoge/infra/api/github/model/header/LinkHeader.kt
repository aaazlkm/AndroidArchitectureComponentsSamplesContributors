package hoge.hogehoge.infra.api.github.model.header

data class LinkHeader(
    val nextPage: Int? = null,
    val lastPage: Int? = null,
    val firstPage: Int? = null,
    val prevPage: Int? = null
) {
    enum class Rel(val text: String) {
        NEXT("next"), LAST("last"), FIRST("first"), PREV("prev");
    }

    companion object {
        const val NAME_IN_HEADER = "link"

        val REL_REGEX = """rel="([a-z]+)"""".toRegex()
        val PAGE_REGEX = """page=(\d)""".toRegex()

        /**
         * GithubのAPIのヘッダーに下記のような形式でページに関する情報が格納されている
         * これらの情報をこのクラスに変換する
         *
         * example:
         * Link: <https://api.github.com/user/repos?page=3&per_page=100>; rel="next",
         * <https://api.github.com/user/repos?page=50&per_page=100>; rel="last"
         *
         * src:
         * https://developer.github.com/v3/#link-header
         *
         * @param text テキスト
         * @return LinkHeader
         */
        fun from(text: String): LinkHeader {
            return text
                .split(",")
                .map {
                    val rel = REL_REGEX.find(it)?.destructured?.component1()
                    val page = PAGE_REGEX.find(it)?.destructured?.component1()?.toIntOrNull()
                    rel to page
                }
                .toMap()
                .let { map ->
                    LinkHeader(
                        nextPage = map[Rel.NEXT.text],
                        lastPage = map[Rel.LAST.text],
                        firstPage = map[Rel.FIRST.text],
                        prevPage = map[Rel.PREV.text]
                    )
                }
        }
    }
}
