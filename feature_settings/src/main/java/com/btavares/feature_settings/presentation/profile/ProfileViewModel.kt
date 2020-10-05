package com.btavares.feature_settings.presentation.profile

import androidx.lifecycle.viewModelScope
import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.usecase.*
import com.btavares.feature_settings.presentation.settings.SettingsViewModel
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class ProfileViewModel(
    private val navManager: NavManager,
    private val args: ProfileFragmentArgs,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.Action>(ViewState()) {



    override fun onLoadData() {
        super.onLoadData()
        init()
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false
    ): BaseViewState


    internal sealed class Action : BaseAction {
        class UserUpdateSuccess(val user : UserDomainModel) : Action()
        object UserUpdateFailure : Action()
    }

    private fun init(){
        viewModelScope.launch {

        }
    }

    override fun onReduceState(viewAction: Action): ViewState {
        TODO("Not yet implemented")
    }


    fun getUserName() = args.userName
    fun getUserEmail() = args.userEmail

    fun navigateBackToSettings(){
        val navDirections = ProfileFragmentDirections.actionProfileFragmentGoBackToSettingsFragment()
        navManager.navigate(navDirections)
    }

    fun updateUserFields(userName : String, userEmail : String) {
        viewModelScope.launch {
            val updateUserResult = async { updateUserUseCase.execute(userName, userEmail) }
            updateUserResult.await().also { result ->
                val action = when(result){
                    is UpdateUserUseCase.Result.Success -> {
                        navigateBackToSettings()
                    }
                    is UpdateUserUseCase.Result.Error -> {

                    }
                }
            }
        }

    }


}