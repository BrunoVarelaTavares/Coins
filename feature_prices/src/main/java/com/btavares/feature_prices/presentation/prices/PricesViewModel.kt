package com.btavares.feature_prices.presentation.prices

import androidx.lifecycle.viewModelScope
import com.btavares.feature_prices.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_prices.domain.model.GlobalMarketDataDomainModel
import com.btavares.feature_prices.domain.usecase.GetCryptocurrencyMarketDataUseCase
import com.btavares.feature_prices.domain.usecase.GetGlobalMarketDataUseCase
import com.btavares.feature_prices.domain.usecase.GetUserNativeCurrencyUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class PricesViewModel (
    private val navManager: NavManager,
    private val getGlobalMarketDataUseCase: GetGlobalMarketDataUseCase,
    private val getCryptocurrencyMarketDataUseCase: GetCryptocurrencyMarketDataUseCase,
    private val getUserNativeCurrencyUseCase: GetUserNativeCurrencyUseCase
) : BaseViewModel<PricesViewModel.ViewState, PricesViewModel.Action>(ViewState()) {


    var list = mutableListOf<CryptocurrencyMarketDomainModel>()


    override fun onLoadData() {
        super.onLoadData()
        init()
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isAllAssetsLoading: Boolean = true,
        val isTopGainersLoading: Boolean = true,
        val isTopLosersLoading: Boolean = true,
        val isAllAssetsDataRequestError: Boolean = false,
        val isTopGainersDataRequestError: Boolean = false,
        val isTopLosersDataRequestError: Boolean = false,
        val globalMarketData: GlobalMarketDataDomainModel? = null,
        val allAssetsMarketData : List<CryptocurrencyMarketDomainModel> = listOf(),
        val topGainersMarketData : List<CryptocurrencyMarketDomainModel> = listOf(),
        val topLosersMarketData : List<CryptocurrencyMarketDomainModel> = listOf()
    ): BaseViewState


    override fun onReduceState(viewAction: Action) = when(viewAction) {

        is Action.GlobalMarketDataSuccess -> state.copy(
            isLoading = false,
            globalMarketData = viewAction.globalMarketData
        )

        is Action.GlobalMarketDataFailure -> state.copy(
            isLoading = false,
            globalMarketData = null
        )

        is Action.CryptocurrencyMarketDataSuccess -> state.copy(
            isAllAssetsLoading = false,
            isAllAssetsDataRequestError = false,
            allAssetsMarketData = viewAction.allAssetsMarketData
        )

        is Action.CryptocurrencyMarketDataFailure -> state.copy(
            isAllAssetsLoading = false,
            isAllAssetsDataRequestError = true,
            allAssetsMarketData = listOf()

        )

        is Action.CryptocurrencyTopGainersDataSuccess -> state.copy(
            isTopGainersLoading = false,
            isTopGainersDataRequestError = false,
            topGainersMarketData = viewAction.topGainersMarketData
        )

        is Action.CryptocurrencyTopGainersDataFailure -> state.copy(
            isTopGainersLoading = false,
            isTopGainersDataRequestError = true,
            topGainersMarketData = listOf()
        )

        is Action.CryptocurrencyTopLosersDataSuccess -> state.copy(
            isLoading = false,
            isTopLosersLoading = false,
            topLosersMarketData = viewAction.topLosersMarketData
        )

        is Action.CryptocurrencyTopLosersDataFailure -> state.copy(
            isTopLosersLoading = false,
            isTopLosersDataRequestError = true,
            topLosersMarketData = listOf()
        )
    }


    internal sealed class Action : BaseAction {
        class GlobalMarketDataSuccess(val globalMarketData : GlobalMarketDataDomainModel) : Action()
        object GlobalMarketDataFailure : Action()
        class CryptocurrencyMarketDataSuccess(val allAssetsMarketData : MutableList<CryptocurrencyMarketDomainModel>) : Action()
        class CryptocurrencyTopGainersDataSuccess(val topGainersMarketData : MutableList<CryptocurrencyMarketDomainModel>) : Action()
        class CryptocurrencyTopLosersDataSuccess(val topLosersMarketData : MutableList<CryptocurrencyMarketDomainModel>) : Action()
        object CryptocurrencyMarketDataFailure : Action()
        object CryptocurrencyTopGainersDataFailure : Action()
        object CryptocurrencyTopLosersDataFailure : Action()
    }

    private fun init(){
        viewModelScope.launch {
            val globalMarketDataResult = async { getGlobalMarketDataUseCase.execute()}

            globalMarketDataResult.await().also { result ->
                val action = when(result){
                    is GetGlobalMarketDataUseCase.Result.Success -> {
                        Action.GlobalMarketDataSuccess(result.data)
                    }
                    is GetGlobalMarketDataUseCase.Result.Error ->
                        Action.GlobalMarketDataFailure
                }
                sendAction(action)
            }

        }
    }

    fun navigateToCryptocurrencyDetailFragment(coinId : String, coinName : String, coinCurrentPrice
                                                        : String, coinPercentage : String, nativeCurrencySymbol :String){
        val navDirection = PricesFragmentDirections.actionPricesToCryptocurrencyDetail(
            coinId,
            coinName ,
            coinCurrentPrice,
            coinPercentage,
            nativeCurrencySymbol)
        navManager.navigate(navDirection)
    }


    fun loadMarketData() = viewModelScope.launch {
            val nativeCurrency = async { getUserNativeCurrencyUseCase.execute() }
            val nativeCurrencyResult = nativeCurrency.await()

            val marketDataResult = async {
                getCryptocurrencyMarketDataUseCase.execute(nativeCurrencyResult.currencyCode) }
                marketDataResult.await().also { result ->
                val action = when(result){
                    is GetCryptocurrencyMarketDataUseCase.Result.Success -> {
                        if (result.data.isEmpty()){
                            sendAction(Action.CryptocurrencyTopGainersDataFailure)
                            sendAction(Action.CryptocurrencyTopLosersDataFailure)
                            Action.CryptocurrencyMarketDataFailure
                        } else {
                            result.data.forEach{
                                it.nativeCurrencySymbol = nativeCurrencyResult.currencySymbol
                            }
                            list.addAll(result.data)
                            list.sortBy { it.marketCapChangePercentageTwentyFourHours }
                            val tt = list.map { it.copy() }.toMutableList()
                            sendAction(Action.CryptocurrencyTopLosersDataSuccess(tt))
                            list.reverse()
                            sendAction(Action.CryptocurrencyTopGainersDataSuccess(list))
                            Action.CryptocurrencyMarketDataSuccess(result.data)

                        }
                    }
                    is GetCryptocurrencyMarketDataUseCase.Result.Error ->{
                        sendAction(Action.CryptocurrencyTopGainersDataFailure)
                        sendAction(Action.CryptocurrencyTopLosersDataFailure)
                        Action.CryptocurrencyMarketDataFailure
                    }
                }
                sendAction(action)
            }
    }



}