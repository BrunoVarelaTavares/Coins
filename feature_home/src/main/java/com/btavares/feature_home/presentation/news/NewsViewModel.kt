package com.btavares.feature_home.presentation.news

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.viewModelScope
import com.btavares.feature_home.domain.model.NewsDomainModel
import com.btavares.feature_home.domain.usecase.GetLatestNewsUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.launch
import java.lang.Exception

internal class NewsViewModel(
    private val navManager: NavManager,
    private val getLatestNewsUseCase: GetLatestNewsUseCase
) : BaseViewModel<NewsViewModel.ViewState, NewsViewModel.Action>(ViewState()) {


    override fun onLoadData() {
        getLatestNews()
    }

    fun navigateToNewsDetails(context : Context, url : String){
        val builder = CustomTabsIntent.Builder()
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setStartAnimations(context,com.btavares.coins.R.anim.slide_in_right, com.btavares.coins.R.anim.slide_out_left)
        builder.setExitAnimations(context, com.btavares.coins.R.anim.slide_in_left, com.btavares.coins.R.anim.slide_out_right)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    fun navigateBackToHome(){
        val navDirections = NewsFragmentDirections.actionNewsGoBackToHomeFragment()
        navManager.navigate(navDirections)
    }


    private fun getLatestNews() = viewModelScope.launch {
        try {
            getLatestNewsUseCase.execute().also { result ->
                val newsAction = when(result){
                    is GetLatestNewsUseCase.Result.Success ->
                        if (result.data.isEmpty()){
                            Action.NewsViewLoadingFailure
                        }else{
                            Action.NewsViewLoadingSuccess(result.data)
                        }

                    is GetLatestNewsUseCase.Result.Error -> Action.NewsViewLoadingFailure
                }
                sendAction(newsAction)
            }

        } catch (e :Exception){
        }
    }

    internal data class ViewState(
       val isLoading: Boolean = true,
       val isError: Boolean = false,
       val mNews: List<NewsDomainModel> = listOf()

    ): BaseViewState


    internal sealed class Action : BaseAction {
        class NewsViewLoadingSuccess(val news : List<NewsDomainModel>) : Action()
        object NewsViewLoadingFailure : Action()
    }

    override fun onReduceState(viewAction: Action) = when(viewAction){

        is Action.NewsViewLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            mNews = viewAction.news
        )

        is Action.NewsViewLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            mNews = listOf()
        )
    }


}