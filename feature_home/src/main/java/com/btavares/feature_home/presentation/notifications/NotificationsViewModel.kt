package com.btavares.feature_home.presentation.notifications

import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState

internal class NotificationsViewModel(
    private val navManager: NavManager
) : BaseViewModel<NotificationsViewModel.ViewState, NotificationsViewModel.Action>(ViewState()) {



    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false
        ): BaseViewState


    internal sealed class Action : BaseAction {
        class NotificationsLoadingSuccess(val news : String) : Action()
        object NotificationsLoadingFailure : Action()
    }

    override fun onReduceState(viewAction: Action): ViewState {
        TODO("Not yet implemented")
    }

    fun navigateBackToHome(){
        val navDirections = NotificationsFragmentDirections.actionNotificationsGoBackToHomeFragment()
        navManager.navigate(navDirections)
    }


}




