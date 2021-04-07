package com.btavares.feature_portfolio.presentation.editcryptocurrency


import androidx.lifecycle.viewModelScope
import com.btavares.feature_portfolio.domain.usecase.SavePortfolioCurrencyValueUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.launch

internal class EditCryptocurrencyViewModel (
    private val navManager: NavManager,
    private val args: EditCryptocurrencyFragmentArgs,
    private val savePortfolioCurrencyValueUseCase: SavePortfolioCurrencyValueUseCase
) : BaseViewModel<EditCryptocurrencyViewModel.ViewState, EditCryptocurrencyViewModel.Action>(ViewState()) {



    internal data class ViewState(
        val isPortfolioBalanceLoading: Boolean = true
    ) : BaseViewState


    internal sealed class Action : BaseAction {
    }


    fun getTitle() =  "Edit ${args.coinName} Portfolio Value"


    fun getNativeCurrencyPortfolioValue() = args.portfolioValue

    override fun onReduceState(viewAction: Action): ViewState {
        TODO("Not yet implemented")
    }

    fun saveCurrencyValue(currencyValue: Double) = viewModelScope.launch {
        savePortfolioCurrencyValueUseCase.execute(args.coinId,currencyValue)
        navigateBackToPortfolioFragment()
    }

    fun navigateBackToPortfolioFragment() {
         val navDirections  = EditCryptocurrencyFragmentDirections.actionEditCryptoGoBackToPortfolioFragment()
         navManager.navigate(navDirections)
    }
}