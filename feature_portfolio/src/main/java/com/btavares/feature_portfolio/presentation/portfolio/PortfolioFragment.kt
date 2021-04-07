package com.btavares.feature_portfolio.presentation.portfolio

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_portfolio.R
import com.btavares.feature_portfolio.presentation.portfolio.recyclerview.PortfolioCryptocurrencyAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.round
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_portfolio.*
import org.kodein.di.generic.instance

internal class PortfolioFragment : InjectionFragment(R.layout.fragment_portfolio){


    private val viewModel: PortfolioViewModel  by instance()


    private val portfolioCryptoAdapter: PortfolioCryptocurrencyAdapter by instance()


    private val stateObserver = Observer<PortfolioViewModel.ViewState> {
        progressBarCryptocurrencies.visible = it.isPortfolioBalanceLoading
        progressBarBalance.visible = it.isPortfolioCryptocurrenciesLoading
        portfolioCollapsingToolbar.title = "${it.currencySymbol}${
            round(it.portfolioBalance)}"
        portfolioCryptoAdapter.portfolioCryptocurrencies = it.portfolioCryptocurrencies


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()

        portfolioRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = portfolioCryptoAdapter
        }

        portfolioCryptoAdapter.setOnDebouncedClickListener {
            viewModel.navigateToEditCryptoFragment(it.id.toString(),it.name.toString(),it.nativeCurrencyValue.toString())
        }



        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }



}