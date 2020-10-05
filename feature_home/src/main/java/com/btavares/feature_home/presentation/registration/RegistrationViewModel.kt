package com.btavares.feature_home.presentation.registration

import android.view.View
import androidx.lifecycle.viewModelScope
import com.btavares.feature_home.domain.usecase.InsertUserUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class RegistrationViewModel(
    private val navManager: NavManager,
    private val insertUserUseCase: InsertUserUseCase
) : BaseViewModel<RegistrationViewModel.ViewState, RegistrationViewModel.Action>(ViewState()) {

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isUserInsertSuccessful : Boolean = false
    ): BaseViewState


    internal sealed class Action : BaseAction {
        class InsertUserSuccess(val insertSuccess : Boolean) : Action()
        object InsertUserFailure : Action()
    }

    override fun onReduceState(viewAction: Action) = when(viewAction){

        is Action.InsertUserSuccess -> state.copy(
            isLoading = false,
            isError = false,
            isUserInsertSuccessful = true
        )

        is Action.InsertUserFailure -> state.copy(
            isLoading = false,
            isError = true,
            isUserInsertSuccessful = false
        )
    }

    fun saveUser(buttons: View?,userFullName : String, userEmail: String){
        viewModelScope.launch {
            val registration = async { insertUserUseCase.execute(userFullName, userEmail) }
            registration.await().also { result ->
                when (result) {
                    is InsertUserUseCase.Result.Success -> {
                        navigateToHomeFragment()
                        buttons?.visibility = View.VISIBLE
                        sendAction(Action.InsertUserSuccess(result.data))
                    }

                    is InsertUserUseCase.Result.Error -> {
                        sendAction(Action.InsertUserFailure)
                    }

                }
            }

        }
    }

    private fun navigateToHomeFragment(){
        val navDirections = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment()
        navManager.navigate(navDirections)
    }



}




