package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import hoge.hogehoge.core.di.viewmodel.ViewModelKey
import hoge.hogehoge.presentation.repository.RepositoryActivity
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsViewModel
import hoge.hogehoge.presentation.repository.repository.RepositoryFragment
import hoge.hogehoge.presentation.user.UserFragment
import hoge.hogehoge.presentation.user.UserViewModel
import hoge.hogehoge.presentation.user.followers.FollowersFragment
import hoge.hogehoge.presentation.user.following.FollowingFragment
import hoge.hogehoge.presentation.user.repositories.RepositoriesFragment
import hoge.hogehoge.presentation.user.stars.StarsFragment

@Module
interface RepositoryActivityModule {
    @Binds
    fun bindsRepositoryActivity(repositoryActivity: RepositoryActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeRepositoryFragment(): RepositoryFragment

    @ContributesAndroidInjector
    fun contributeContributorsFragment(): ContributorsFragment

    @ContributesAndroidInjector
    fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    fun contributeFollowersFragment(): FollowersFragment

    @ContributesAndroidInjector
    fun contributeFollowingFragment(): FollowingFragment

    @ContributesAndroidInjector
    fun contributeRepositoriesFragment(): RepositoriesFragment

    @ContributesAndroidInjector
    fun contributeStarsFragment(): StarsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsViewModel::class)
    fun bindsContributorsViewModel(
        contributorsViewModel: ContributorsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindsUserViewModel(
        userViewModel: UserViewModel
    ): ViewModel
}
