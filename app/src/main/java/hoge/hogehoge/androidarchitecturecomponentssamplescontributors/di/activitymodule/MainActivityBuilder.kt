package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoge.hogehoge.presentation.MainActivity

@Module
interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}
