package com.btavares.feature_home.presentation.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_home.R
import com.btavares.feature_home.presentation.home.recyclerview.MarketDataFavoritesAdapter
import com.btavares.feature_home.presentation.home.recyclerview.MarketDataTopMoversAdapter
import com.btavares.feature_home.presentation.home.recyclerview.NewsAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.round
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.generic.instance

class HomeFragment : InjectionFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel  by instance()

    private val  favoritesAdapter: MarketDataFavoritesAdapter by instance()

    private val topMoversAdapter : MarketDataTopMoversAdapter by instance()

    private val newsAdapter: NewsAdapter by instance()


    private val stateObserver = Observer<HomeViewModel.ViewState> {
        progressBarWatchlist.visible = it.isWatchlistLoading
        progressBarTopMovers.visible = it.isTopMoversLoading
        progressBarNewsTopStories.visible = it.isNewsLoading
        watchlistErrorLayout.visible = it.isWatchlistDataRequestError
        topMoversErrorLayout.visible = it.isTopMoversDataRequestError
        newsErrorLayout.visible = it.isNewsDataRequestError
        homeCollapsingToolbar.title = "${it.currencySymbol}${
            round(it.portfolioBalance)}"
        favoritesAdapter.cryptocurrencyMarketData = it.cryptocurrencyMarketData
        topMoversAdapter.topMoversData = it.cryptocurrencyMarketTopMovers
        newsAdapter.mNewsData = it.mNews
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()

        watchlistRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }

        newsAdapter.setOnDebouncedClickListener {
            viewModel.navigateToNewsDetails(context,it.url)
        }

        moreStoriesLayout.setOnDebouncedClickListener {
            viewModel.navigateToNewsLayout()
        }

        ivNotifications.setOnDebouncedClickListener {
            viewModel.navigateToNotificationsFragment()
        }

        favoritesAdapter.setOnDebouncedClickListener {

            viewModel.navigateToCoinMarketDetailFragment(it.id.orEmpty(),
                                                         it.name.orEmpty(),
                                                         it.currentPrice.toString(),
                                                         it.marketCapChangePercentageTwentyFourHours.toString(),
                                                         it.nativeCurrencySymbol)
        }

        topMoversAdapter.setOnDebouncedClickListener {
            viewModel.navigateToCoinMarketDetailFragment(it.id.orEmpty(),
                                                         it.name.orEmpty(),
                                                         it.currentPrice.toString(),
                                                         it.marketCapChangePercentageTwentyFourHours.toString(),
                                                         it.nativeCurrencySymbol)
        }

        topMoversRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = topMoversAdapter

        }

        topNewsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = newsAdapter
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}