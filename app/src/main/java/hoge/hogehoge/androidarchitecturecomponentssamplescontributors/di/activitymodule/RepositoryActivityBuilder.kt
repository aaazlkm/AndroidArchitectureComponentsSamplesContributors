package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoge.hogehoge.presentation.repository.RepositoryActivity

@Module
interface RepositoryActivityBuilder {
    @ContributesAndroidInjector(modules = [RepositoryActivityModule::class])
    fun contributeRepositoryActivity(): RepositoryActivity
}
