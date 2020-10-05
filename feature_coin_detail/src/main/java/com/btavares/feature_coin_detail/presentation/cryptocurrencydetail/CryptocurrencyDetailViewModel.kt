package com.btavares.feature_coin_detail.presentation.cryptocurrencydetail

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.viewModelScope
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyDomainModel
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import com.btavares.feature_coin_detail.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_coin_detail.domain.model.NewsDomainModel
import com.btavares.feature_coin_detail.domain.usecase.*
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyChartDataUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyInfoUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyMarketChartByRangeUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetNewsByCryptocurrencyUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetUserNativeCurrencyUseCase
import com.btavares.library_base.presentation.extension.*
import com.btavares.library_base.presentation.viewmodel.BaseAction
import com.btavares.library_base.presentation.viewmodel.BaseViewModel
import com.btavares.library_base.presentation.viewmodel.BaseViewState
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class CryptocurrencyDetailViewModel(
    private val getCryptocurrencyChartDataUseCase: GetCryptocurrencyChartDataUseCase,
    private val getCryptocurrencyMarketChartByRangeUseCase: GetCryptocurrencyMarketChartByRangeUseCase,
    private val getCryptocurrencyInfoUseCase: GetCryptocurrencyInfoUseCase,
    private val getNewsByCryptocurrencyUseCase: GetNewsByCryptocurrencyUseCase,
    private val getUserNativeCurrencyUseCase: GetUserNativeCurrencyUseCase,
    private val checkIfCryptocurrencyExistsInWatchlistUseCase: CheckIfCryptocurrencyExistsInWatchlistUseCase,
    private val insertCryptocurrencyIntoWatchlistUseCase: InsertCryptocurrencyIntoWatchlistUseCase,
    private val deleteCryptocurrencyFromWatchlistUseCase: DeleteCryptocurrencyFromWatchlistUseCase,
    private val args: CryptocurrencyDetailFragmentArgs
) : BaseViewModel<CryptocurrencyDetailViewModel.ViewState, CryptocurrencyDetailViewModel.Action>(ViewState()) {



    private lateinit var domainModel : CryptocurrencyMarketChartDomainModel
    private lateinit var nativeCurrency: NativeCurrencyDomainModel

    internal data class ViewState(
        val isChartDataLoading : Boolean = true,
        val isCurrencyInfoLoading : Boolean = true,
        val isCurrencyNewsLoading : Boolean = true,
        val chartDataError : Boolean = false,
        val currencyInfoError : Boolean = false,
        val currencyNewsError : Boolean = false,
        val cryptocurrencyExists : Boolean = false,
        val coinLineData : LineData? = null,
        val currency: CryptocurrencyDomainModel? = null,
        val currencyNews: List<NewsDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class  CoinMarketChartSuccess(val coinLineData : LineData?) : Action()
        object CoinMarketChartFailure : Action()
        class  CurrencyInfoDataSuccess(val currency: CryptocurrencyDomainModel?) : Action()
        object CurrencyInfoDataFailure : Action()
        class  CoinNewsLoadingSuccess(val currencyNews : List<NewsDomainModel>) : Action()
        object CoinNewsLoadingFailure : Action()
        class  CheckCryptocurrencyExistsSuccess(val cryptocurrencyExists: Boolean) : Action()
        object CheckCryptocurrencyExistsFailure : Action()

    }

    override fun onLoadData() {
        super.onLoadData()
        load()
    }

    override fun onReduceState(viewAction: Action) = when(viewAction) {
        is Action.CoinMarketChartSuccess -> state.copy(
            isChartDataLoading = false,
            chartDataError = false,
            coinLineData = viewAction.coinLineData
        )

        is Action.CoinMarketChartFailure -> state.copy(
            isChartDataLoading = false,
            chartDataError = true,
            coinLineData = null
        )

        is Action.CurrencyInfoDataSuccess -> state.copy(
            isCurrencyInfoLoading = false,
            currencyInfoError = false,
            currency = viewAction.currency
        )

        is Action.CurrencyInfoDataFailure -> state.copy(
            isCurrencyInfoLoading = false,
            currencyInfoError = true,
            currency = null
        )

        is Action.CoinNewsLoadingSuccess -> state.copy(
            isCurrencyNewsLoading = false,
            currencyNewsError = false,
            currencyNews = viewAction.currencyNews
        )

        is Action.CoinNewsLoadingFailure -> state.copy(
            isCurrencyNewsLoading = false,
            currencyNewsError = true,
            currencyNews = listOf()
        )

        is Action.CheckCryptocurrencyExistsSuccess -> state.copy(
            cryptocurrencyExists = viewAction.cryptocurrencyExists
        )

        is Action.CheckCryptocurrencyExistsFailure -> state.copy(
            cryptocurrencyExists = false
        )

    }

    fun getCryptocurrencyLabel() : String? {
        return "${args.coinName} Price"
    }

    fun getToolbarTitle() : String {
        return "${args.nativeCurrencySymbol}${"%.2f".format(args.coinCurrentPrice.toDouble())}"
    }

    fun getToolbarSubtitle() : String {
        return "${roundNumber(args.coinPercentage.toDouble())}"
    }

    fun setSubtitleTextColor() : Int {
        return  setPercentageTextViewColor(args.coinPercentage.toDouble())
    }


    fun navigateToNewsDetails(context: Context, url : String){

        val builder = CustomTabsIntent.Builder()
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setStartAnimations(context,com.btavares.coins.R.anim.slide_in_right, com.btavares.coins.R.anim.slide_out_left)
        builder.setExitAnimations(context, com.btavares.coins.R.anim.slide_in_left, com.btavares.coins.R.anim.slide_out_right)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }


    private fun load() {
        viewModelScope.launch {
            val nativeCurrencyResult = async { getUserNativeCurrencyUseCase.execute() }
            nativeCurrencyResult.await().also {
                when (it) {
                    is GetUserNativeCurrencyUseCase.Result.Success -> nativeCurrency = it.data
                }
            }


            val exists = async { checkIfCryptocurrencyExistsInWatchlistUseCase.execute(args.coinId) }
            exists.await().also { result ->
                val action = when(result){
                    is CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Success ->{
                        Action.CheckCryptocurrencyExistsSuccess(result.data)
                    }
                    is CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Error ->
                        Action.CheckCryptocurrencyExistsFailure
                }
                sendAction(action)
            }


            if (::nativeCurrency.isInitialized) {
                val cryptoData =  async {
                    getCryptocurrencyChartDataUseCase.execute(
                        args.coinId,
                        nativeCurrency.currencyCode,
                        "1"
                    )
                }

                cryptoData.await().also {
                    when (it) {
                        is GetCryptocurrencyChartDataUseCase.Result.Success -> {
                            if (it.data == null){
                                sendAction(Action.CoinMarketChartFailure)
                            } else {
                                domainModel = it.data
                                sendAction(Action.CoinMarketChartSuccess(setUpChartData(it.data)))
                            }

                        }
                        is GetCryptocurrencyChartDataUseCase.Result.Error -> sendAction(Action.CoinMarketChartFailure)
                    }

                }

            } else
                sendAction(Action.CoinMarketChartFailure)

            val currencyInfoData =  async {
                getCryptocurrencyInfoUseCase.execute(
                    args.coinId
                )
            }


           val currencyNewsData = async { getNewsByCryptocurrencyUseCase.execute(args.coinId)}


            currencyInfoData.await().also {
                when (it) {
                    is GetCryptocurrencyInfoUseCase.Result.Success -> sendAction(Action.CurrencyInfoDataSuccess(it.data))
                    is GetCryptocurrencyInfoUseCase.Result.Error -> sendAction(Action.CurrencyInfoDataFailure)
                }
            }

            currencyNewsData.await().also {
                when (it) {
                    is GetNewsByCryptocurrencyUseCase.Result.Success -> sendAction(Action.CoinNewsLoadingSuccess(it.data))
                    is GetNewsByCryptocurrencyUseCase.Result.Error -> sendAction(Action.CoinNewsLoadingFailure)
                }
            }

        }
    }


    private fun checkIfCryptoExistsInWatchlist(){
        viewModelScope.launch {
            val exists = async { checkIfCryptocurrencyExistsInWatchlistUseCase.execute(args.coinId) }
            exists.await().also { result ->
                val action = when(result){
                    is CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Success ->{
                        Action.CheckCryptocurrencyExistsSuccess(result.data)
                    }
                    is CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Error ->
                        Action.CheckCryptocurrencyExistsFailure
                }
                sendAction(action)
            }
        }
    }


    fun getChartDataByHour(){
        viewModelScope.launch{
            checkIfCryptoExistsInWatchlist()
            getChartDataByRange()
        }

    }

    fun getChartDataByDay(){
        viewModelScope.launch{
            checkIfCryptoExistsInWatchlist()
            val oneDay = "1"
            getChartData(oneDay)
        }

    }

    fun getChartDataByWeek(){
        viewModelScope.launch{
            val sevenDays = "7"
            checkIfCryptoExistsInWatchlist()
            getChartData(sevenDays)
        }

    }

    fun getChartDataByMonth(){
        viewModelScope.launch {
            checkIfCryptoExistsInWatchlist()
            getChartData(getLastMonthInDays())
        }

    }

    fun getChartDataByYear(){
        viewModelScope.launch {
            checkIfCryptoExistsInWatchlist()
            getChartData(getLastYearInDays())
        }

    }


    fun getChartDataAll(){
        viewModelScope.launch {
            checkIfCryptoExistsInWatchlist()
            val maxDays = "max"
            getChartData(maxDays)
        }
    }


    fun insertCryptocurrencyToWatchlist(){
        viewModelScope.launch {
            insertCryptocurrencyIntoWatchlistUseCase.execute(args.coinId)
        }
    }

    fun deleteCryptocurrencyFromWatchlist(){
        viewModelScope.launch {
            deleteCryptocurrencyFromWatchlistUseCase.execute(args.coinId)
        }
    }


    private suspend fun getChartData(days : String) = viewModelScope.launch {
        if (::nativeCurrency.isInitialized) {
            val chartData =  async {
                getCryptocurrencyChartDataUseCase.execute(
                    args.coinId,
                    nativeCurrency.currencyCode,
                    days
                )
            }

            chartData.await().also { result ->
                val action = when(result){
                    is GetCryptocurrencyChartDataUseCase.Result.Success ->
                        if (result.data == null){
                            Action.CoinMarketChartFailure
                        }else{
                            val chart = setUpChartData(result.data)
                            domainModel = result.data
                            Action.CoinMarketChartSuccess(chart)
                        }

                    is GetCryptocurrencyChartDataUseCase.Result.Error -> Action.CoinMarketChartFailure
                }
                sendAction(action)
            }
        } else
              sendAction(Action.CoinMarketChartFailure)

    }


    private suspend fun getChartDataByRange() = viewModelScope.launch {

        val lastHourTimestamp = getLastHourDateTime()
        val currentHourTimestamp = getCurrentDateTime()
        val cryptoData =  async {
            getCryptocurrencyMarketChartByRangeUseCase.execute(
                args.coinId,
                nativeCurrency.currencyCode,
                lastHourTimestamp,
                currentHourTimestamp
            )
        }

        cryptoData.await().also { result ->
            val action = when(result){
                is GetCryptocurrencyMarketChartByRangeUseCase.Result.Success ->
                    if (result.data == null){
                        Action.CoinMarketChartFailure
                    }else{
                        val chartData = setUpChartData(result.data)
                        domainModel = result.data
                        Action.CoinMarketChartSuccess(chartData)
                    }

                is GetCryptocurrencyMarketChartByRangeUseCase.Result.Error
                    -> Action.CoinMarketChartFailure
            }
            sendAction(action)
        }
    }

    fun getNativeCurrencySymbol()  = args.nativeCurrencySymbol

    fun getPriceDatetime(price : Float?) : String?{
        return domainModel.prices.find { it.price?.toFloat()!!.equals(price) }?.date
    }

    private fun setUpChartData(cryptocurrencyChartData: CryptocurrencyMarketChartDomainModel?) : LineData {

        val chartEntryList = mutableListOf<Entry>()
        cryptocurrencyChartData?.prices?.forEachIndexed{ index, element ->
            if (element.price != null)
             chartEntryList.add(Entry(index.toFloat(),element.price.toFloat()))
        }

        val lineDataSet = LineDataSet(chartEntryList, "")
        lineDataSet.lineWidth = 4f
        lineDataSet.color = Color.BLUE
        lineDataSet.highLightColor = Color.BLUE
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        val dataSet = arrayListOf<ILineDataSet>()

        dataSet.add(lineDataSet)

        val lineData = LineData(dataSet)
        lineData.setDrawValues(false)

        return lineData
    }

}