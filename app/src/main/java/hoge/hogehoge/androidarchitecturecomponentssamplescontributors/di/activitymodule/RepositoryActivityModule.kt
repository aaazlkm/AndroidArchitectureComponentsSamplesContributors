package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoge.hogehoge.presentation.repository.RepositoryActivity
import hoge.hogehoge.presentation.repository.contributor.ContributorFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragment
import hoge.hogehoge.presentation.repository.repository.RepositoryFragment

@Module
interface RepositoryActivityModule {
    @Binds
    fun bindsRepositoryActivity(repositoryActivity: RepositoryActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeRepositoryFragment(): RepositoryFragment

    @ContributesAndroidInjector
    fun contributeContributorsFragment(): ContributorsFragment

    @ContributesAndroidInjector
    fun contributeContributorFragment(): ContributorFragment
}
