package hoge.hogehoge.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerFragment
import hoge.hogehoge.presentation.R
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : DaggerFragment() {
    protected val compositeDisposable = CompositeDisposable()

    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    //region lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setupActionBar()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        setLocadingView(false)
        hideKeyboard()
        compositeDisposable.clear()
    }

    //endregion

    open fun setupActionBar(title: String = "") {
        baseActivity?.setupActionBar(title)
    }

    fun hideKeyboard() {
        activity?.currentFocus?.let {
            val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
            manager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun showToast(message: String) {
        baseActivity?.showToast(message)
    }

    fun showDialog(
        title: String,
        message: String,
        okText: String = getString(R.string.common_dialog_ok),
        doOnClickOk: (() -> Unit)? = null,
        cancelText: String = getString(R.string.common_dialog_cancel),
        doOnClickCancel: (() -> Unit)? = null
    ) {
        baseActivity?.showDialog(
            title,
            message,
            okText,
            doOnClickOk,
            cancelText,
            doOnClickCancel
        )
    }

    fun setLocadingView(needShow: Boolean) {
        baseActivity?.setLoadingView(needShow)
    }
}
