package com.btavares.feature_prices.presentation.prices.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_prices.R
import com.btavares.feature_prices.presentation.prices.PricesViewModel
import com.btavares.feature_prices.presentation.prices.recyclerview.CryptocurrencyMarketDataAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragement_top_losers.*
import kotlinx.android.synthetic.main.fragment_all_assets.*
import org.kodein.di.generic.instance

class FragmentTopLosers  : InjectionFragment(R.layout.fragement_top_losers) {


    private val viewModel: PricesViewModel by instance()

    private val cryptocurrencyMarketDataAdapter : CryptocurrencyMarketDataAdapter by instance()

    private val stateObserver = Observer<PricesViewModel.ViewState> { it
        topLosersProgressBar.visible = it.isTopLosersLoading
        topLosersAssetsErrorLayout.visible = it.isTopLosersDataRequestError
        cryptocurrencyMarketDataAdapter.marketData = it.topLosersMarketData
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topLosersRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = cryptocurrencyMarketDataAdapter
        }

        cryptocurrencyMarketDataAdapter.setOnDebouncedClickListener {

            viewModel.navigateToCryptocurrencyDetailFragment(it.id.orEmpty(),
                it.name.orEmpty(),
                it.currentPrice.toString(),
                it.marketCapChangePercentageTwentyFourHours.toString(),
                it.nativeCurrencySymbol)
        }


        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadMarketData()
    }


}