package hoge.hogehoge.presentation.repository.contributors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.common.DiffUtilAdapter
import hoge.hogehoge.presentation.databinding.ItemContributorBinding
import hoge.hogehoge.presentation.databinding.ItemProgressBinding
import hoge.hogehoge.presentation.repository.contributors.ContributorsAdapter.Item.ProgressItem.VIEW_TYPE
import io.reactivex.disposables.CompositeDisposable

class ContributorsAdapter(
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) : DiffUtilAdapter<ContributorsAdapter.ViewHolder, ContributorsAdapter.Item>(compositeDisposable) {

    //region Listener

    interface OnItemClickListener {
        fun onItemClicked(binding: ItemContributorBinding, contributor: Contributor)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    //endregion

    sealed class Item {
        data class ContributorItem(val contributor: Contributor) : Item() {
            companion object {
                const val VIEW_TYPE = 0
            }
        }

        object ProgressItem : Item() {
            const val VIEW_TYPE = 1
        }

        val viewType: Int
            get() = when (this) {
                is ContributorItem -> ContributorItem.VIEW_TYPE
                is ProgressItem -> VIEW_TYPE
            }
    }

    sealed class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        data class Contributor(val binding: ItemContributorBinding) : ViewHolder(binding.root)

        data class Progress(val binding: ItemProgressBinding) : ViewHolder(binding.root)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null

    //region RecyclerView.Adapter override methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Item.ContributorItem.VIEW_TYPE -> {
                val contributorBinding: ItemContributorBinding = ItemContributorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder.Contributor(contributorBinding)
            }
            Item.ProgressItem.VIEW_TYPE -> {
                val progressBinding: ItemProgressBinding = ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder.Progress(progressBinding)
            }
            else -> throw IllegalArgumentException("illegal view type. please confirm view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.Contributor -> {
                val item = items[position] as Item.ContributorItem
                val contributor = item.contributor
                with(holder.binding) {
                    loadUserIcon(imageView, contributor)
                    nameText.text = contributor.login ?: ""
                    contributionsText.text = "${contributor.contributions}"
                    container.setOnClickListener { onItemClickListener?.onItemClicked(holder.binding, contributor) }
                }
            }
            is ViewHolder.Progress -> {
                onLoadMoreListener?.onLoadMore()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    //endregion

    //region DiffUtilAdapter override methods

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return if (oldItem is Item.ContributorItem && newItem is Item.ContributorItem) {
            oldItem.contributor.id == newItem.contributor.id
        } else if (oldItem is Item.ProgressItem && newItem is Item.ProgressItem) {
            true
        } else {
            return false
        }
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return if (oldItem is Item.ContributorItem && newItem is Item.ContributorItem) {
            oldItem == newItem
        } else if (oldItem is Item.ProgressItem && newItem is Item.ProgressItem) {
            true
        } else {
            return false
        }
    }

    //endregion

    fun insertContributorsAndResetProgress(contributors: List<Contributor>, doOnCompleted: (() -> Unit)? = null) {
        if (contributors.isEmpty()) return
        items.filter { it !is Item.ProgressItem }
            .toMutableList()
            .apply {
                addAll(contributors.map { Item.ContributorItem(it) })
                add(Item.ProgressItem)
            }
            .let {
                updateItems(it) { doOnCompleted?.invoke() }
            }
    }

    fun getContributors(): List<Contributor> {
        return items.mapNotNull { it as? Item.ContributorItem }.map { it.contributor }
    }

    fun clearContributors() {
        updateItems(listOf())
    }

    fun removeProgressView() {
        updateItems(items.filter { it !is Item.ProgressItem })
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.onItemClickListener = listener
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener?) {
        this.onLoadMoreListener = listener
    }

    //region load user icon

    private fun loadUserIcon(imageView: ImageView, contributor: Contributor) {
        imageView.load(contributor.avatarUrl) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
            error(R.drawable.placeholder)
            transformations(CircleCropTransformation())
        }
    }

    //endregion
}