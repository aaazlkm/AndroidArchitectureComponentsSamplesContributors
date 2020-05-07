package hoge.hogehoge.presentation.user

import android.content.Context
import androidx.fragment.app.Fragment
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.user.followers.FollowersFragment
import hoge.hogehoge.presentation.user.following.FollowingFragment
import hoge.hogehoge.presentation.user.repositories.RepositoriesFragment
import hoge.hogehoge.presentation.user.stars.StarsFragment

enum class TabItem {
    REPOSITORIES,
    STARS,
    FOLLOWERS,
    FOLLOWING;

    fun getTitle(context: Context): String {
        return when (this) {
            REPOSITORIES -> context.getString(R.string.fragment_user_tab_repositories)
            STARS -> context.getString(R.string.fragment_user_tab_stars)
            FOLLOWERS -> context.getString(R.string.fragment_user_tab_followers)
            FOLLOWING -> context.getString(R.string.fragment_user_tab_following)
        }
    }

    fun createFragment(): Fragment {
        return when (this) {
            REPOSITORIES -> RepositoriesFragment()
            STARS -> StarsFragment()
            FOLLOWERS -> FollowersFragment()
            FOLLOWING -> FollowingFragment()
        }
    }
}
