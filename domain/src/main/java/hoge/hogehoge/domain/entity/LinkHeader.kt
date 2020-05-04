package hoge.hogehoge.domain.entity

import hoge.hogehoge.infra.api.github.model.header.RawLinkHeader

/**
 * リンクヘッダーのモデルクラス
 * APIによって値がないことがあるのでnullable
 *
 * @property prevPage Int
 * @property nextPage Int
 * @property firstPage Int
 * @property lastPage Int
 */
data class LinkHeader(
    val prevPage: Int?,
    val nextPage: Int?,
    val firstPage: Int?,
    val lastPage: Int?
) {
    companion object {
        /**
         * APIで取得したデータからContributorを生成する
         * デフォルトの値をいれる場合はここで行う
         *
         * @param rawLinkHeader RawLinkHeader
         * @return LinkHeader
         */
        fun from(rawLinkHeader: RawLinkHeader): LinkHeader {
            return LinkHeader(
                prevPage = rawLinkHeader.prevPage,
                nextPage = rawLinkHeader.nextPage,
                firstPage = rawLinkHeader.firstPage,
                lastPage = rawLinkHeader.lastPage
            )
        }
    }
}
