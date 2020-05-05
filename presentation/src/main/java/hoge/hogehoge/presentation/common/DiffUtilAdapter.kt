package hoge.hogehoge.presentation.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hoge.hogehoge.domain.result.Result
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

abstract class DiffUtilAdapter<VIEW_HOLDER, ITEM>(
    compositeDisposable: CompositeDisposable
) : RecyclerView.Adapter<VIEW_HOLDER>() where VIEW_HOLDER : RecyclerView.ViewHolder {
    private val itemsProcessor =
        BehaviorProcessor.createDefault<Pair<List<ITEM>, (() -> Unit)?>>(listOf<ITEM>() to null)
    val items: List<ITEM>
        get() = itemsProcessor.value!!.first // 初期化しているためnullになり得ない

    /**
     * アイテムの更新が成功したかどうかを通知する
     * Booleanの値に意味はない
     */
    private val updateResultProcessor = PublishProcessor.create<Result<Boolean>>()
    val updateResult: Flowable<Result<Boolean>> = updateResultProcessor.observeOn(AndroidSchedulers.mainThread())

    init {
        // 前の値と比較している
        Flowables.zip(itemsProcessor, itemsProcessor.skip(1))
            .observeOn(Schedulers.computation())
            .map { (oldItem, newItem) ->
                val oldList = oldItem.first
                val newList = newItem.first
                object : DiffUtil.Callback() {
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])

                    override fun getOldListSize(): Int = oldList.size

                    override fun getNewListSize(): Int = newList.size
                } to newItem.second
            }
            .map { DiffUtil.calculateDiff(it.first) to it.second }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val (diffResult, doOnCompleted) = it
                    diffResult.dispatchUpdatesTo(this)
                    doOnCompleted?.invoke()
                    updateResultProcessor.onNext(Result.success(true))
                },
                onError = {
                    updateResultProcessor.onNext(Result.failure(it))
                }
            )
            .addTo(compositeDisposable)
    }

    /**
     * 二つのアイテムがで同じものかどうかを判断する
     * (idなどで判断)
     *
     * @param oldItem ITEM
     * @param newItem ITEM
     * @return Boolean
     */
    open fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem == newItem
    }

    /**
     * 二つのアイテムが同じ内容であるかを判断する
     * areItemsTheSameがtrueの時に呼ばれる
     *
     * @param oldItem ITEM
     * @param newItem ITEM
     * @return Boolean
     */
    open fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem == newItem
    }

    /**
     * DiffUtilを使用して変更の分だけアイテムを更新する
     *
     * @param newItems List<ITEM>
     * @param doOnCompleted (() -> Unit)?
     */
    fun updateItems(newItems: List<ITEM>, doOnCompleted: (() -> Unit)? = null) {
        itemsProcessor.onNext(newItems to doOnCompleted)
    }
}
