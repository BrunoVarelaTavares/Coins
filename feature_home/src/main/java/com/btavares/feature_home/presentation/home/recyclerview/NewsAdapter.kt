package com.btavares.feature_home.presentation.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.btavares.feature_home.R
import com.btavares.feature_home.domain.model.NewsDomainModel
import com.btavares.library_base.delegate.observer
import com.btavares.library_base.presentation.extension.getNewsDate
import com.btavares.library_base.presentation.extension.setNewsItemLayoutVisibility
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import kotlinx.android.synthetic.main.news_item.view.*

internal class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var mNewsData: List<NewsDomainModel> by observer(listOf()){
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((news : NewsDomainModel) -> Unit)? = null

    internal inner class ViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsDomainModel: NewsDomainModel){
            itemView.tvNewsTitle.text = newsDomainModel.title
            itemView.ivNewsImage.load(newsDomainModel.thumbnail)
            itemView.tvNewsSourceName.text = newsDomainModel?.sourceDomainModel?.name
            itemView.tvNewsDate.text = getNewsDate(newsDomainModel.publishedAt)
            itemView.tvFirstCoinName.text = newsDomainModel?.coins?.first()?.name
            newsDomainModel?.coins?.let {
              val isVisible =  setNewsItemLayoutVisibility(it)
                if (isVisible){
                    itemView.linearLayoutCoin.visibility = View.VISIBLE
                    itemView.tvSecondCoinName.text = it[1]?.name
                }
            }

            itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(newsDomainModel) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int = mNewsData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(mNewsData[position])
    }

    fun setOnDebouncedClickListener(listener : (news : NewsDomainModel) -> Unit) {
        this.onDebouncedClickListener = listener
    }
}