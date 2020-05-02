package hoge.hogehoge.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerAppCompatActivity
import hoge.hogehoge.presentation.R

open class BaseActivity : DaggerAppCompatActivity() {
    private lateinit var loadingView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingView = this.layoutInflater.inflate(R.layout.view_loading, findViewById(android.R.id.content))
    }

    fun setupActionBar(title: String) {
        val canBack = supportFragmentManager.backStackEntryCount > 1
        supportActionBar?.setDisplayHomeAsUpEnabled(canBack)
        supportActionBar?.title = title
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(
        title: String,
        message: String,
        positiveText: String = getString(R.string.common_dialog_ok),
        doOnClickPositive: (() -> Unit)? = null,
        negativeText: String = getString(R.string.common_dialog_cancel),
        doOnClickNegative: (() -> Unit)? = null

    ) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { _, _ ->
                doOnClickPositive?.invoke()
            }
            .setNegativeButton(negativeText) { _, _ ->
                doOnClickNegative?.invoke()
            }
            .show()
    }

    fun setLoadingView(needShow: Boolean) {
        loadingView.visibility = if (needShow) View.VISIBLE else View.GONE
    }
}
