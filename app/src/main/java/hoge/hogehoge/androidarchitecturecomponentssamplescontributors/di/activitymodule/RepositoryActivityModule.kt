package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import hoge.hogehoge.core.di.viewmodel.ViewModelKey
import hoge.hogehoge.presentation.repository.RepositoryActivity
import hoge.hogehoge.presentation.repository.contributor.ContributorFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsViewModel::class)
    fun bindsContributorsViewModel(
        contributorsViewModel: ContributorsViewModel
    ): ViewModel
}
