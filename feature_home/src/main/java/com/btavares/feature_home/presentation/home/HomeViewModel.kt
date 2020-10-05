package com.btavares.feature_home.presentation.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.viewModelScope
import com.btavares.feature_home.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_home.domain.model.NewsDomainModel
import com.btavares.feature_home.domain.usecase.*
import com.btavares.library_base.presentation.extension.getFirstFiveElementsFromList
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.abs


internal class HomeViewModel (
    private val navManager: NavManager,
    private val getMarketDataUseCase: GetMarketDataUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getUserNativeCurrencyUseCase: GetUserNativeCurrencyUseCase,
    private val getUserPortfolioBalanceUseCase: GetUserPortfolioBalanceUseCase,
    private val getCryptocurrenciesWatchlistIds: GetCryptocurrenciesWatchlistIds,
    private val checkIfUserExistsUseCase: CheckIfUserExistsUseCase
) : BaseViewModel<HomeViewModel.ViewState, HomeViewModel.Action>(ViewState()){

    override fun onLoadData() {
        super.onLoadData()
        init()
    }

    override fun onReduceState(viewAction: Action) = when (viewAction){
        is Action.MarketDataLoadingSuccess -> state.copy(
            isWatchlistLoading = false,
            isWatchlistDataRequestError = false,
            cryptocurrencyMarketData = viewAction.cryptocurrencyMarketData
        )

        is Action.MarketDataLoadingFailure -> state.copy(
            isWatchlistLoading = false,
            isWatchlistDataRequestError = true,
            cryptocurrencyMarketData = listOf()
        )

        is Action.MarketDataTopFiveSuccess -> state.copy(
            isTopMoversLoading = false,
            isTopMoversDataRequestError = false,
            cryptocurrencyMarketTopMovers = viewAction.cryptocurrencyMarketTopFiveData
        )

        is Action.MarketDataTopFiveFailure -> state.copy(
            isTopMoversLoading = false,
            isTopMoversDataRequestError = true,
            cryptocurrencyMarketTopMovers = listOf()
        )

        is Action.NativeCurrencySymbolLoadingSuccess -> state.copy(
            currencySymbol = viewAction.nativeCurrency.currencySymbol
        )

        is Action.NativeCurrencySymbolFailure -> state.copy(
            currencySymbol = ""
        )

        is Action.PortfolioBalanceLoadingSuccess -> state.copy(
            portfolioBalance = viewAction.portfolioBalance
        )

        is Action.PortfolioBalanceSymbolFailure -> state.copy(
            portfolioBalance = 0.0
        )

        is Action.NewsLoadingSuccess -> state.copy(
            isNewsLoading = false,
            isNewsDataRequestError = false,
            mNews = viewAction.news
        )

        is Action.NewsLoadingFailure -> state.copy(
            isNewsLoading = false,
            isNewsDataRequestError = true,
            mNews = listOf()
        )

    }

    fun navigateToNewsDetails(context: Context, url : String){

        val builder = CustomTabsIntent.Builder()
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setStartAnimations(
            context,
            com.btavares.coins.R.anim.slide_in_right,
            com.btavares.coins.R.anim.slide_out_left)
        builder.setExitAnimations(
            context,
            com.btavares.coins.R.anim.slide_in_left,
            com.btavares.coins.R.anim.slide_out_right)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }


    fun navigateToNewsLayout(){
        val navDirections = HomeFragmentDirections.actionHomeMoreNewsToNews()
        navManager.navigate(navDirections)
    }

    fun navigateToRegistrationFragment(){
        val navDirections = HomeFragmentDirections.actionHomeToRegistrationFragment()
        navManager.navigate(navDirections)
    }

    fun navigateToNotificationsFragment(){
        val navDirections = HomeFragmentDirections.actionHomeToNotificationsFragment()
        navManager.navigate(navDirections)
    }

    fun navigateToCoinMarketDetailFragment(coinId : String, coinName : String, coinCurrentPrice : String, coinPercentage : String, nativeCurrencySymbol :String){
        val navDirection = HomeFragmentDirections.actionHomeToCoinMarketDetailFragment(coinId, coinName ,coinCurrentPrice, coinPercentage, nativeCurrencySymbol)
        navManager.navigate(navDirection)
    }
    private fun init() = viewModelScope.launch  {
        try {
            var ifUserExists = false
            val executeCheckUser = async { checkIfUserExistsUseCase.execute() }
            executeCheckUser.await().also {
                when (it) {
                    is CheckIfUserExistsUseCase.Result.Success -> ifUserExists = it.data
                }
            }

            if (ifUserExists) {

                val nativeCurrencyResult = async { getUserNativeCurrencyUseCase.execute() }
                var nativeCurrency = NativeCurrencyDomainModel("EUR", "€")
                nativeCurrencyResult.await().also {
                    when (it) {
                        is GetUserNativeCurrencyUseCase.Result.Success -> {
                            nativeCurrency = it.data
                            sendAction(Action.NativeCurrencySymbolLoadingSuccess(it.data))
                        }
                        is GetUserNativeCurrencyUseCase.Result.Error -> sendAction(Action.NativeCurrencySymbolFailure)
                    }
                }


                val balance = async { getUserPortfolioBalanceUseCase.execute() }
                balance.await().also { result ->
                    val action = when (result) {
                        is GetUserPortfolioBalanceUseCase.Result.Success ->
                            Action.PortfolioBalanceLoadingSuccess(result.data)
                        is GetUserPortfolioBalanceUseCase.Result.Error ->
                            Action.PortfolioBalanceSymbolFailure
                    }
                    sendAction(action)
                }


                val watchlistIds = async { getCryptocurrenciesWatchlistIds.execute() }
                watchlistIds.await().also { result ->
                    when (result) {
                        is GetCryptocurrenciesWatchlistIds.Result.Success -> {
                            val cryptocurrencyIds = result.data.joinToString { it }
                             loadCryptocurrencyMarketData(cryptocurrencyIds,nativeCurrency)
                        }
                        is GetCryptocurrenciesWatchlistIds.Result.Error -> {
                            sendAction(Action.MarketDataLoadingFailure)
                        }
                    }
                }


                val getMarketDataDeferred = async { getMarketDataUseCase.execute(nativeCurrency.currencyCode)}
                getMarketDataDeferred.await().also { result ->
                    val action = when (result) {
                        is GetMarketDataUseCase.Result.Success ->
                            if (result.data.isEmpty()) {
                                Action.MarketDataTopFiveFailure
                            } else {
                                var topCoinsMarketPercentage: MutableList<CryptocurrencyMarketDomainModel> = mutableListOf()
                                topCoinsMarketPercentage.addAll(result.data)
                                topCoinsMarketPercentage.sortByDescending {
                                    it.marketCapChangePercentageTwentyFourHours?.let { percentage -> abs(percentage)}
                                }
                                topCoinsMarketPercentage = getFirstFiveElementsFromList(topCoinsMarketPercentage)

                                topCoinsMarketPercentage.forEach {
                                    it.nativeCurrencySymbol = nativeCurrency.currencySymbol
                                }
                                Action.MarketDataTopFiveSuccess(topCoinsMarketPercentage)
                            }

                        is GetMarketDataUseCase.Result.Error -> Action.MarketDataTopFiveFailure
                    }
                    sendAction(action)
                }


                val getTopNewsDeferred = async { getNewsUseCase.execute() }
                getTopNewsDeferred.await().also { result ->
                    val newsAction = when (result) {
                        is GetNewsUseCase.Result.Success ->
                            if (result.data.isEmpty()) {
                                Action.NewsLoadingFailure
                            } else {
                                val newsTopFive = getFirstFiveElementsFromList(result.data)
                                Action.NewsLoadingSuccess(newsTopFive)
                            }

                        is GetNewsUseCase.Result.Error -> Action.NewsLoadingFailure
                    }
                    sendAction(newsAction)
                }

            } else {
                navigateToRegistrationFragment()
            }

        }catch (e : Exception){

        }

    }

    private fun loadCryptocurrencyMarketData(ids : String, nativeCurrency: NativeCurrencyDomainModel){
        viewModelScope.launch {
        val getMarketDataDeferred = async { getMarketDataUseCase.execute(nativeCurrency.currencyCode, ids) }
        getMarketDataDeferred.await().also { result ->
            val action = when (result) {
                is GetMarketDataUseCase.Result.Success ->
                    if (result.data.isEmpty()) {
                        Action.MarketDataLoadingFailure
                    } else {
                        result.data.forEach {
                            it.nativeCurrencySymbol = nativeCurrency.currencySymbol
                        }
                        Action.MarketDataLoadingSuccess(result.data)
                    }
                is GetMarketDataUseCase.Result.Error -> Action.MarketDataLoadingFailure
            }
            sendAction(action)
        }

        }

    }


    internal data class ViewState(
        val isWatchlistLoading: Boolean = true,
        val isTopMoversLoading: Boolean = true,
        val isNewsLoading: Boolean = true,
        val isWatchlistDataRequestError: Boolean = false,
        val isTopMoversDataRequestError: Boolean = false,
        val isNewsDataRequestError: Boolean = false,
        val currencySymbol: String = "",
        val portfolioBalance: Double = 0.0,
        val cryptocurrencyMarketData: List<CryptocurrencyMarketDomainModel> = listOf(),
        val cryptocurrencyMarketTopMovers: List<CryptocurrencyMarketDomainModel> = listOf(),
        val mNews: List<NewsDomainModel> = listOf()
    ): BaseViewState


    internal sealed class Action : BaseAction{
        class MarketDataLoadingSuccess(val cryptocurrencyMarketData : List<CryptocurrencyMarketDomainModel>) : Action()
        class MarketDataTopFiveSuccess(val cryptocurrencyMarketTopFiveData : MutableList<CryptocurrencyMarketDomainModel>) : Action()
        class NewsLoadingSuccess(val news : List<NewsDomainModel>) : Action()
        class NativeCurrencySymbolLoadingSuccess(val nativeCurrency : NativeCurrencyDomainModel) : Action()
        class PortfolioBalanceLoadingSuccess(val portfolioBalance : Double) : Action()
        object PortfolioBalanceSymbolFailure : Action()
        object NativeCurrencySymbolFailure : Action()
        object MarketDataTopFiveFailure : Action()
        object MarketDataLoadingFailure : Action()
        object NewsLoadingFailure : Action()
    }

}