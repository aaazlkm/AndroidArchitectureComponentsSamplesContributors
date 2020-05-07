package hoge.hogehoge.domain.entity

import hoge.hogehoge.infra.api.github.model.body.RawUser

data class User(
    val avatarUrl: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val createdAt: String?,
    val email: String?,
    val eventsUrl: String?,
    val followers: Int,
    val followersUrl: String?,
    val following: Int,
    val followingUrl: String?,
    val gistsUrl: String?,
    val gravatarId: String?,
    val hireable: Boolean,
    val htmlUrl: String?,
    val id: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    val nodeId: String?,
    val organizationsUrl: String?,
    val publicGists: Int,
    val publicRepos: Int,
    val receivedEventsUrl: String?,
    val reposUrl: String?,
    val siteAdmin: Boolean,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val type: String?,
    val updatedAt: String?,
    val url: String?
) {
    companion object {
        /**
         * APIで取得したデータからUserを生成する
         * デフォルトの値をいれる場合はここで行う
         *
         * @param rawUser RawUser
         * @return User
         */
        fun from(rawUser: RawUser): User {
            val avatarUrl = rawUser.avatarUrl
            val bio = rawUser.bio
            val blog = rawUser.blog
            val company = rawUser.company
            val createdAt = rawUser.createdAt
            val email = rawUser.email
            val eventsUrl = rawUser.eventsUrl
            val followers = rawUser.followers ?: 0
            val followersUrl = rawUser.followersUrl
            val following = rawUser.following ?: 0
            val followingUrl = rawUser.followingUrl
            val gistsUrl = rawUser.gistsUrl
            val gravatarId = rawUser.gravatarId
            val hireable = rawUser.hireable ?: false
            val htmlUrl = rawUser.htmlUrl
            val id = rawUser.id
            val location = rawUser.location
            val login = rawUser.login
            val name = rawUser.name
            val nodeId = rawUser.nodeId
            val organizationsUrl = rawUser.organizationsUrl
            val publicGists = rawUser.publicGists ?: 0
            val publicRepos = rawUser.publicRepos ?: 0
            val receivedEventsUrl = rawUser.receivedEventsUrl
            val reposUrl = rawUser.reposUrl
            val siteAdmin = rawUser.siteAdmin ?: false
            val starredUrl = rawUser.starredUrl
            val subscriptionsUrl = rawUser.subscriptionsUrl
            val type = rawUser.type
            val updatedAt = rawUser.updatedAt
            val url = rawUser.url

            return User(
                avatarUrl,
                bio,
                blog,
                company,
                createdAt,
                email,
                eventsUrl,
                followers,
                followersUrl,
                following,
                followingUrl,
                gistsUrl,
                gravatarId,
                hireable,
                htmlUrl,
                id,
                location,
                login,
                name,
                nodeId,
                organizationsUrl,
                publicGists,
                publicRepos,
                receivedEventsUrl,
                reposUrl,
                siteAdmin,
                starredUrl,
                subscriptionsUrl,
                type,
                updatedAt,
                url
            )
        }
    }
}
