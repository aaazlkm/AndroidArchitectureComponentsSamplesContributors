package hoge.hogehoge.androidarchitecturecomponentssamplescontributors

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
