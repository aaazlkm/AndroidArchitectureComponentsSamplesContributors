package hoge.hogehoge.domain.entity

import hoge.hogehoge.infra.api.github.model.body.RawContributor

data class Contributor(
    val avatarUrl: String?,
    val contributions: Int,
    val eventsUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val gravatarId: String,
    val htmlUrl: String?,
    val id: Int?,
    val login: String?,
    val nodeId: String?,
    val organizationsUrl: String?,
    val receivedEventsUrl: String?,
    val reposUrl: String?,
    val siteAdmin: Boolean?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val type: String?,
    val url: String?
) {
    companion object {
        /**
         * APIで取得したデータからContributorを生成する
         * デフォルトの値をいれる場合はここで行う
         *
         * @param rawContributor RawContributor
         * @return Contributor
         */
        fun from(rawContributor: RawContributor): Contributor {
            val avatarUrl = rawContributor.avatarUrl
            val contributions = rawContributor.contributions ?: 0
            val eventsUrl = rawContributor.eventsUrl
            val followersUrl = rawContributor.followersUrl
            val followingUrl = rawContributor.followingUrl
            val gistsUrl = rawContributor.gistsUrl
            val gravatarId = rawContributor.gravatarId ?: ""
            val htmlUrl = rawContributor.htmlUrl
            val id = rawContributor.id
            val login = rawContributor.login
            val nodeId = rawContributor.nodeId
            val organizationsUrl = rawContributor.organizationsUrl
            val receivedEventsUrl = rawContributor.receivedEventsUrl
            val reposUrl = rawContributor.reposUrl
            val siteAdmin = rawContributor.siteAdmin
            val starredUrl = rawContributor.starredUrl
            val subscriptionsUrl = rawContributor.subscriptionsUrl
            val type = rawContributor.type
            val url = rawContributor.url

            return Contributor(
                avatarUrl,
                contributions,
                eventsUrl,
                followersUrl,
                followingUrl,
                gistsUrl,
                gravatarId,
                htmlUrl,
                id,
                login,
                nodeId,
                organizationsUrl,
                receivedEventsUrl,
                reposUrl,
                siteAdmin,
                starredUrl,
                subscriptionsUrl,
                type,
                url
            )
        }
    }
}
