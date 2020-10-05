package com.btavares.feature_coin_detail.presentation.cryptocurrencydetail

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_coin_detail.R
import com.btavares.feature_coin_detail.presentation.cryptocurrencydetail.recyclerview.NewsAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_cryptocurrency_detail.*
import org.kodein.di.generic.instance

internal class CryptocurrencyDetailFragment: InjectionFragment(R.layout.fragment_cryptocurrency_detail){

    private val viewModel : CryptocurrencyDetailViewModel by instance()

    private val currencyNewsAdapter: NewsAdapter by instance()


    private val stateObserver = Observer<CryptocurrencyDetailViewModel.ViewState>{
        coinMarketDetailChartProgressBar.isVisible = it.isChartDataLoading
        coinDescriptionProgressBar.isVisible = it.isCurrencyInfoLoading
        coinNewsProgressBar.isVisible = it.isCurrencyNewsLoading
        newsErrorAnimation.isVisible = it.currencyNewsError
        ivFavoritesUnchecked.isVisible = !it.cryptocurrencyExists
        btnUnFollow.isVisible = it.cryptocurrencyExists
        ivFavoritesChecked.isVisible = it.cryptocurrencyExists
        btnFollow.isVisible = !it.cryptocurrencyExists

        coinChart.data = it.coinLineData
        currencyNewsAdapter.mNewsData = it.currencyNews
        val priceLabel = "${it.currency?.name} ${getString(R.string.price_label)}"
        val aboutLabel = "${getString(R.string.about_label)} ${it.currency?.name}"
        val newsLabel = "${it.currency?.name} ${getString(R.string.news_label)}"
        tvMarkerPriceLabel.text = priceLabel
        tvAboutCurrencyLabel.text = aboutLabel
        tvCurrentNewsTitle.text = newsLabel
        tvCurrencyDescription.text = HtmlCompat.fromHtml(it.currency?.description?: "", 0)
        coinChart.invalidate()
        coinChart.notifyDataSetChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()

        ivCoinMarketDetailBackArrow.setOnDebouncedClickListener {
            activity?.onBackPressed()
        }


        currencyNewsAdapter.setOnDebouncedClickListener {
            viewModel.navigateToNewsDetails(context,it.url)
        }

        coinChart.apply {
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false

            // setDrawGridBackground(false)
            setScaleEnabled(false)


        }

        coinNewsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context
            )

            adapter = currencyNewsAdapter
        }


        collapsingToolbar.title = viewModel.getToolbarTitle()
        collapsingToolbar.subtitle = viewModel.getToolbarSubtitle()
        collapsingToolbar.setCollapsedSubtitleTextColor(viewModel.setSubtitleTextColor())
        collapsingToolbar.setExpandedSubtitleTextColor(viewModel.setSubtitleTextColor())
        btnChartHour.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataByHour()
        }

        btnChartDay.isSelected = true
        btnChartDay.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataByDay()
        }


        btnChartWeek.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataByWeek()
        }

        btnChartMonth.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataByMonth()
        }

        btnChartYear.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataByYear()
        }

        btnChartAll.setOnDebouncedClickListener {
            coinMarketDetailChartProgressBar.visibility = View.VISIBLE
            viewModel.getChartDataAll()
        }

        btnFollow.setOnDebouncedClickListener {
            btnFollow.visibility = View.INVISIBLE
            ivFavoritesUnchecked.visibility = View.INVISIBLE
            btnUnFollow.visibility = View.VISIBLE
            ivFavoritesChecked.visibility = View.VISIBLE
            viewModel.insertCryptocurrencyToWatchlist()

        }

        btnUnFollow.setOnDebouncedClickListener {
            btnUnFollow.visibility = View.INVISIBLE
            ivFavoritesChecked.visibility = View.INVISIBLE
            btnFollow.visibility = View.VISIBLE
            ivFavoritesUnchecked.visibility = View.VISIBLE
            viewModel.deleteCryptocurrencyFromWatchlist()

        }

        ivFavoritesChecked.setOnDebouncedClickListener {
            btnUnFollow.visibility = View.INVISIBLE
            ivFavoritesChecked.visibility = View.INVISIBLE
            btnFollow.visibility = View.VISIBLE
            ivFavoritesUnchecked.visibility = View.VISIBLE
            viewModel.deleteCryptocurrencyFromWatchlist()
        }

        ivFavoritesUnchecked.setOnDebouncedClickListener {
            btnFollow.visibility = View.INVISIBLE
            ivFavoritesUnchecked.visibility = View.INVISIBLE
            btnUnFollow.visibility = View.VISIBLE
            ivFavoritesChecked.visibility = View.VISIBLE
            viewModel.insertCryptocurrencyToWatchlist()
        }




        coinChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val entry = e
                val title = entry?.y.toString()
                collapsingToolbar.isTitleEnabled = true
                collapsingToolbar.title = "${viewModel.getNativeCurrencySymbol()}${title}"
                collapsingToolbar.subtitle = " "
                val textLabel = viewModel.getPriceDatetime(title.toFloat())
                tvMarkerPriceLabel.text = textLabel
            }

        })

        coinChart.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, motion: MotionEvent?): Boolean {
                if(motion?.action ==  MotionEvent.ACTION_UP || motion?.action ==  MotionEvent.ACTION_CANCEL) {
                    tvMarkerPriceLabel.text = viewModel.getCryptocurrencyLabel()
                    collapsingToolbar.title = viewModel.getToolbarTitle()
                    collapsingToolbar.subtitle = viewModel.getToolbarSubtitle()
                    return true
                }
                return false
            }
        })

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()

    }


}