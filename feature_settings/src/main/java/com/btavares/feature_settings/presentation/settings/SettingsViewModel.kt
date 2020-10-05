package com.btavares.feature_settings.presentation.settings

import androidx.lifecycle.viewModelScope
import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.usecase.GetCurrentNativeCurrencyUseCase
import com.btavares.feature_settings.domain.usecase.GetNativeCurrenciesUseCase
import com.btavares.feature_settings.domain.usecase.GetUserUseCase
import com.btavares.feature_settings.domain.usecase.UpdateUserNativeCurrencyUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val navManager: NavManager,
    private val getUserUseCase: GetUserUseCase,
    private val getNativeCurrenciesUseCase: GetNativeCurrenciesUseCase,
    private val getCurrentNativeCurrencyUseCase: GetCurrentNativeCurrencyUseCase,
    private val updateUserNativeCurrencyUseCase: UpdateUserNativeCurrencyUseCase
) : BaseViewModel<SettingsViewModel.ViewState, SettingsViewModel.Action>(ViewState()) {


    private var mCurrencies = emptyArray<String>()
    private var currentNativeCurrencyIndex = -1

    override fun onLoadData() {
        super.onLoadData()
        init()
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val user : UserDomainModel? = null,
        val currency : NativeCurrencyDomainModel? = null
    ): BaseViewState


    internal sealed class Action : BaseAction {
        class UserLoadingSuccess(val user : UserDomainModel) : Action()
        object UserLoadingFailure : Action()
        class NativeCurrencyLoadingSuccess(val currency : NativeCurrencyDomainModel) : Action()
        object NativeCurrencyLoadingFailure : Action()
    }

    override fun onReduceState(viewAction: Action) = when(viewAction) {

        is Action.UserLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            user = viewAction.user
        )

        is Action.UserLoadingFailure -> state.copy(
            isLoading = false,
            isError = true

        )


        is Action.NativeCurrencyLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            currency = viewAction.currency
        )

        is Action.NativeCurrencyLoadingFailure -> state.copy(
            isLoading = false,
            isError = true

        )

    }

    private fun init(){
        viewModelScope.launch {
            val userResult = async { getUserUseCase.execute() }
            userResult.await().also { result ->
                val action = when(result){
                    is GetUserUseCase.Result.Success ->
                        Action.UserLoadingSuccess(result.data)
                    is GetUserUseCase.Result.Error ->
                        Action.UserLoadingFailure
                }
                sendAction(action)
            }

            val currenciesResult = async { getNativeCurrenciesUseCase.execute()}
            mCurrencies = currenciesResult.await().map { it.currencyCode }.toTypedArray()

            val nativeCurrencyResult = async { getCurrentNativeCurrencyUseCase.execute()}
            nativeCurrencyResult.await().also { result ->
                val action = when(result){
                    is GetCurrentNativeCurrencyUseCase.Result.Success -> {
                        currentNativeCurrencyIndex = mCurrencies.indexOf(result.data.currencyCode)
                        Action.NativeCurrencyLoadingSuccess(result.data)
                    }
                    is GetCurrentNativeCurrencyUseCase.Result.Error ->
                        Action.NativeCurrencyLoadingFailure
                }
                sendAction(action)
            }
        }
    }


    fun getCurrencies() : Array<String> {
        return mCurrencies
    }

    fun getNativeCurrencyIndex() : Int {
       return currentNativeCurrencyIndex
    }


    fun navigateToProfileFragment(userName : String, userEmail :String){
        val navDirections = SettingsFragmentDirections.actionSettingsFragToProfileFrag(userName,userEmail)
        navManager.navigate(navDirections)
    }


    fun updateNativeCurrency(currencyCode : String){
        viewModelScope.launch {
            val update = async { updateUserNativeCurrencyUseCase.execute(currencyCode)}
            update.await()
            init()
        }
    }

}