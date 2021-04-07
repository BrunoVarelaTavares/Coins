package com.btavares.feature_portfolio.presentation.portfolio

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.btavares.feature_portfolio.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel
import com.btavares.feature_portfolio.domain.usecase.GetAllPortfolioCryptocurrencies
import com.btavares.feature_portfolio.domain.usecase.GetPortfolioBalance
import com.btavares.feature_portfolio.domain.usecase.GetUserNativeCurrencyUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class PortfolioViewModel(
    private val navManager: NavManager,
    private val getUserNativeCurrencyUseCase: GetUserNativeCurrencyUseCase,
    private val getPortfolioBalance: GetPortfolioBalance,
    private val getAllPortfolioCryptocurrencies: GetAllPortfolioCryptocurrencies
) : BaseViewModel<PortfolioViewModel.ViewState, PortfolioViewModel.Action>(ViewState()) {


    override fun onLoadData() {
        super.onLoadData()
        init()
    }

    internal data class ViewState(
        val isPortfolioBalanceLoading: Boolean = true,
        val isPortfolioBalanceError: Boolean = false,
        val isPortfolioCryptocurrenciesLoading: Boolean = true,
        val isPortfolioCryptocurrenciesError: Boolean = false,
        val currencySymbol: String = "",
        val portfolioBalance: Double? = null,
        val portfolioCryptocurrencies : List<PortfolioCryptocurrencyDomainModel> = listOf()
    ) : BaseViewState


    internal sealed class Action : BaseAction {
        class PortfolioBalanceSuccess(val balance: Double) : Action()
        object PortfolioBalanceFailure : Action()
        class PortfolioCryptocurrenciesSuccess(val currencies: List<PortfolioCryptocurrencyDomainModel>) : Action()
        object PortfolioCryptocurrenciesFailure : Action()
        class NativeCurrencySymbolLoadingSuccess(val nativeCurrency : NativeCurrencyDomainModel) : Action()
        object NativeCurrencySymbolFailure : Action()
    }
    override fun onReduceState(viewAction: Action) = when (viewAction) {

        is  Action.PortfolioBalanceSuccess -> state.copy(
            isPortfolioBalanceLoading = false,
            isPortfolioBalanceError = false,
            portfolioBalance = viewAction.balance
        )

        is Action.PortfolioBalanceFailure -> state.copy(
            isPortfolioBalanceLoading = false,
            isPortfolioBalanceError = true,
            portfolioBalance = 0.0
        )

        is Action.PortfolioCryptocurrenciesSuccess -> state.copy(
            isPortfolioCryptocurrenciesLoading = false,
            isPortfolioCryptocurrenciesError = false,
            portfolioCryptocurrencies = viewAction.currencies
        )

        is Action.PortfolioCryptocurrenciesFailure -> state.copy(
            isPortfolioCryptocurrenciesLoading = false,
            isPortfolioCryptocurrenciesError = true,
            portfolioCryptocurrencies = listOf()
            
        )

        is Action.NativeCurrencySymbolLoadingSuccess -> state.copy(
            currencySymbol = viewAction.nativeCurrency.currencySymbol
        )

        is Action.NativeCurrencySymbolFailure -> state.copy(
            currencySymbol = ""
        )
    }


     private fun init() = viewModelScope.launch  {
         try {


             val balance = async { getPortfolioBalance.execute() }
             balance.await().also { result ->
                 val action = when (result) {
                     is GetPortfolioBalance.Result.Success ->
                         Action.PortfolioBalanceSuccess(result.data)
                     is GetPortfolioBalance.Result.Error ->
                         Action.PortfolioBalanceFailure
                 }
                 sendAction(action)
             }

             val nativeCurrencyResult = async { getUserNativeCurrencyUseCase.execute() }
             var nativeCurrency = NativeCurrencyDomainModel()
             nativeCurrencyResult.await().also {
                 when (it) {
                     is GetUserNativeCurrencyUseCase.Result.Success -> {
                         nativeCurrency = it.data
                         sendAction(Action.NativeCurrencySymbolLoadingSuccess(it.data))
                     }
                     is GetUserNativeCurrencyUseCase.Result.Error -> sendAction(Action.NativeCurrencySymbolFailure)
                 }
             }


             val cryptocurrencies = async { getAllPortfolioCryptocurrencies.execute() }
             cryptocurrencies.await().also { result ->
                 val action = when (result) {
                     is GetAllPortfolioCryptocurrencies.Result.Success -> {
                         result.data.forEach {
                             it.nativeCurrencySymbol = nativeCurrency.currencySymbol
                         }
                         Action.PortfolioCryptocurrenciesSuccess(result.data)
                     }
                     is GetAllPortfolioCryptocurrencies.Result.Error ->
                         Action.PortfolioCryptocurrenciesFailure
                 }
                 sendAction(action)
             }
         } catch (e : Exception){

         }

     }

    fun navigateToEditCryptoFragment(coinId: String, coinName : String, coinValue : String){
        val navDirections = PortfolioFragmentDirections.actionPortfolioToEditCrypto(coinId,coinName,coinValue)
        navManager.navigate(navDirections)
    }



}