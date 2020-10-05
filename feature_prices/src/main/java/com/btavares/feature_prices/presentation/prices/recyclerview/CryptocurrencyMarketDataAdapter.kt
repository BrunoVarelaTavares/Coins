package com.btavares.feature_prices.presentation.prices.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.btavares.feature_prices.R
import com.btavares.feature_prices.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.library_base.delegate.observer
import com.btavares.library_base.presentation.extension.roundNumber
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.extension.setPercentageTextViewColor
import kotlinx.android.synthetic.main.cryptocurrency_market_data_view_item.view.*
import kotlin.collections.ArrayList
import kotlin.math.log

internal class CryptocurrencyMarketDataAdapter: RecyclerView.Adapter<CryptocurrencyMarketDataAdapter.ViewHolder>() {

    var marketData: List<CryptocurrencyMarketDomainModel> by observer(listOf()){
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((marketData: CryptocurrencyMarketDomainModel) -> Unit)? = null

    internal inner class ViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView){


        fun bind(domainModel: CryptocurrencyMarketDomainModel){
            itemView.tvCoinName.text = domainModel.name;
            itemView.tvCoinSymbol.text = domainModel.symbol
            itemView.ivCoinLogo.load(domainModel.image)
            itemView.tvCurrentPrice.text = "${domainModel.nativeCurrencySymbol}${"%.2f".format(domainModel.currentPrice)}"
            itemView.tvCoinPercentage.text = roundNumber(domainModel.marketCapChangePercentageTwentyFourHours)
            itemView.tvCoinPercentage.setTextColor(setPercentageTextViewColor(domainModel.marketCapChangePercentageTwentyFourHours))

            itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(domainModel) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cryptocurrency_market_data_view_item, parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int = marketData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marketData[position])
    }

    fun setOnDebouncedClickListener(listener : (marketData : CryptocurrencyMarketDomainModel) -> Unit){
        this.onDebouncedClickListener = listener
    }
}