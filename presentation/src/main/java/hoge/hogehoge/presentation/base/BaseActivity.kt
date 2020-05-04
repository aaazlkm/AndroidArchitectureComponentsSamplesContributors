package hoge.hogehoge.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerAppCompatActivity
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.databinding.ViewLoadingBinding

open class BaseActivity : DaggerAppCompatActivity() {
    private lateinit var loadingView: ViewLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingView = ViewLoadingBinding.inflate(this.layoutInflater, findViewById(android.R.id.content), true)
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
        loadingView.root.visibility = if (needShow) View.VISIBLE else View.GONE
    }
}
