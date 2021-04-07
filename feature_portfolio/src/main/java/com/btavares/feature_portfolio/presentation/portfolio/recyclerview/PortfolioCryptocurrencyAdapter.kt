package com.btavares.feature_portfolio.presentation.portfolio.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.btavares.feature_portfolio.R
import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel
import com.btavares.library_base.delegate.observer
import com.btavares.library_base.presentation.extension.roundBigDecimal
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import kotlinx.android.synthetic.main.portfolio_cryptocurrency_item.view.*
import java.util.*

internal class PortfolioCryptocurrencyAdapter : RecyclerView.Adapter<PortfolioCryptocurrencyAdapter.ViewHolder>() {

    var portfolioCryptocurrencies: List<PortfolioCryptocurrencyDomainModel> by observer(listOf()){
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((cryptocurrency: PortfolioCryptocurrencyDomainModel) -> Unit)? = null

    internal inner class ViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SetTextI18n")
        fun bind(domainModel: PortfolioCryptocurrencyDomainModel) {
            itemView.tvCryptoName.text = domainModel.name
            itemView.ivCryptoLogo.load(domainModel.imageUrl)
            itemView.tvNativeCurrencyValue.text = "${domainModel.nativeCurrencySymbol}${domainModel.nativeCurrencyValue}"
            itemView.tvCryptocurrencyValue.text =
                "${roundBigDecimal(domainModel.cryptocurrencyValue)} ${domainModel.symbol?.toUpperCase(
                    Locale.ROOT)}"

            itemView.setOnDebouncedClickListener {
                onDebouncedClickListener?.invoke(domainModel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.portfolio_cryptocurrency_item, parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int = portfolioCryptocurrencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portfolioCryptocurrencies[position])
    }

    fun setOnDebouncedClickListener(listener : (cryptocurrency: PortfolioCryptocurrencyDomainModel) -> Unit){
        this.onDebouncedClickListener = listener
    }
}