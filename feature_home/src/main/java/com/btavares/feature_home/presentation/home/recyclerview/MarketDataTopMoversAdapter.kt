package com.btavares.feature_home.presentation.home.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.btavares.feature_home.R
import com.btavares.feature_home.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.library_base.delegate.observer
import com.btavares.library_base.presentation.extension.roundNumber
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.extension.setPercentageTextViewColor
import kotlinx.android.synthetic.main.market_data_favorites_item.view.*

internal class MarketDataTopMoversAdapter : RecyclerView.Adapter<MarketDataTopMoversAdapter.ViewHolder>(){

    var topMoversData: List<CryptocurrencyMarketDomainModel> by observer(listOf()){
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((cryptocurrencyMarket: CryptocurrencyMarketDomainModel) -> Unit)? = null

    internal inner class ViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView){

        @SuppressLint("SetTextI18n")
        fun bind(domainModel: CryptocurrencyMarketDomainModel){
            itemView.tvCoinName.text = domainModel.name
            itemView.ivCoinLogo.load(domainModel.image)
            itemView.tvCurrentPrice.text = "${domainModel.nativeCurrencySymbol}${"%.2f".format(domainModel.currentPrice)}"
            itemView.tvCoinPercentage.text = roundNumber(domainModel.marketCapChangePercentageTwentyFourHours)
            itemView.tvCoinPercentage.setTextColor(setPercentageTextViewColor(domainModel.marketCapChangePercentageTwentyFourHours))

            itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(domainModel) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketDataTopMoversAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.market_data_top_movers_item, parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int = topMoversData.size

    override fun onBindViewHolder(holder: MarketDataTopMoversAdapter.ViewHolder, position: Int) {
        holder.bind(topMoversData[position])
    }


    fun setOnDebouncedClickListener(listener : (cryptocurrencyMarket : CryptocurrencyMarketDomainModel) -> Unit){
        this.onDebouncedClickListener = listener
    }



/*
*     fun setOnDebouncedClickListener(listener : (news : NewsDomainModel) -> Unit) {
        this.onDebouncedClickListener = listener
    }
*
*
*
* */
}