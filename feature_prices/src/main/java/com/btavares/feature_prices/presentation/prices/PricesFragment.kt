package com.btavares.feature_prices.presentation.prices

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_prices.R
import com.btavares.feature_prices.presentation.prices.adapter.FragmentAdapter
import com.btavares.feature_prices.presentation.prices.recyclerview.CryptocurrencyMarketDataAdapter
import com.btavares.feature_prices.presentation.prices.recyclerview.SearchCryptocurrencyAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_all_assets.*
import kotlinx.android.synthetic.main.fragment_prices.*
import org.kodein.di.generic.instance

class PricesFragment : InjectionFragment(R.layout.fragment_prices) {

    private val viewModel: PricesViewModel by instance()

    private val cryptocurrencyAdapter : SearchCryptocurrencyAdapter by instance()

    private val stateObserver = Observer<PricesViewModel.ViewState> {
        pricesProgressBar.isVisible = it.isLoading
        tvLabel24hours.visible = !(it.isLoading)
        searchViewErrorLayout.visible = it.isAllAssetsDataRequestError
        it.globalMarketData?.percentageTextColor?.let { color ->
            pricesCollapsingToolbar.setCollapsedTitleTextColor(color)
            pricesCollapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(color)) }
        pricesCollapsingToolbar.title = it.globalMarketData?.marketCapPercentage
        cryptocurrencyAdapter.marketData = it.allAssetsMarketData.toMutableList()
        loadTabLayout()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()

        pricesCollapsingToolbar.setExpandedTitleTextAppearance(
            com.btavares.coins.R.style.ExpandedAppBar)


        ivSearchIcon.setOnDebouncedClickListener {
            setUpSearchLayout()
        }

        tvCancelSearchView.setOnDebouncedClickListener {
            setUpCancelButton()
        }

        searchViewRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = cryptocurrencyAdapter
        }


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cryptocurrencyAdapter.filter.filter(newText)
                return false
            }

        })

        cryptocurrencyAdapter.setOnDebouncedClickListener {

            viewModel.navigateToCryptocurrencyDetailFragment(it.id.orEmpty(),
                it.name.orEmpty(),
                it.currentPrice.toString(),
                it.marketCapChangePercentageTwentyFourHours.toString(),
                it.nativeCurrencySymbol)
        }




        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()


    }


    private fun setUpSearchLayout(){
        viewModel.loadMarketData()
        cryptocurrencyAdapter.clear()
        appBarLayoutPrices.visibility = View.GONE
        rlPrices.visibility = View.GONE
        rlSearchView.visibility = View.VISIBLE
        searchView.onActionViewExpanded()
        searchView.requestFocus()
    }

    private fun setUpCancelButton() {
        appBarLayoutPrices.visibility = View.VISIBLE
        rlPrices.visibility = View.VISIBLE
        rlSearchView.visibility = View.GONE
        searchView.setQuery("",false)
        cryptocurrencyAdapter.clear()
        loadTabLayout()
    }

    private fun loadTabLayout(){
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())
        viewPager.adapter = FragmentAdapter(requireContext(),
            activity?.supportFragmentManager,lifecycle)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_title_all_assets)
                1 -> tab.text = getString(R.string.tab_title_top_gainers)
                2 -> tab.text = getText(R.string.tab_title_top_losers)
            }

        }.attach()

    }

}