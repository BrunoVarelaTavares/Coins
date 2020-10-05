package com.btavares.feature_coin_detail.presentation.cryptocurrencydetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.btavares.feature_coin_detail.presentation.cryptocurrencydetail.CryptocurrencyDetailViewModel.ViewState
import com.btavares.feature_coin_detail.domain.DomainFixtures
import com.btavares.feature_coin_detail.domain.usecase.*
import com.btavares.library_test_utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CryptocurrencyDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @MockK
    internal lateinit var mockGetCryptocurrencyChartDataUseCase: GetCryptocurrencyChartDataUseCase

    @MockK
    internal lateinit var mockGetCryptocurrencyMarketChartByRangeUseCase: GetCryptocurrencyMarketChartByRangeUseCase

    @MockK
    internal lateinit var mockGetCryptocurrencyInfoUseCase: GetCryptocurrencyInfoUseCase

    @MockK
    internal lateinit var mockGetNewsByCryptocurrencyUseCase: GetNewsByCryptocurrencyUseCase

    @MockK
    internal lateinit var mockGetUserNativeCurrencyUseCase : GetUserNativeCurrencyUseCase

    @MockK
    internal lateinit var mockCheckIfCryptocurrencyExistsInWatchlistUseCase: CheckIfCryptocurrencyExistsInWatchlistUseCase

    @MockK
    internal lateinit var mockInsertCryptocurrencyIntoWatchlistUseCase: InsertCryptocurrencyIntoWatchlistUseCase

    @MockK
    internal lateinit var mockDeleteCryptocurrencyFromWatchlistUseCase: DeleteCryptocurrencyFromWatchlistUseCase

    @MockK
    internal lateinit var mockCryptocurrencyDetailFragmentArgs: CryptocurrencyDetailFragmentArgs

    private lateinit var viewModel: CryptocurrencyDetailViewModel



    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        viewModel = CryptocurrencyDetailViewModel(
            mockGetCryptocurrencyChartDataUseCase,
            mockGetCryptocurrencyMarketChartByRangeUseCase,
            mockGetCryptocurrencyInfoUseCase,
            mockGetNewsByCryptocurrencyUseCase,
            mockGetUserNativeCurrencyUseCase,
            mockCheckIfCryptocurrencyExistsInWatchlistUseCase,
            mockInsertCryptocurrencyIntoWatchlistUseCase,
            mockDeleteCryptocurrencyFromWatchlistUseCase,
            mockCryptocurrencyDetailFragmentArgs
        )

    }


    @Test
    fun `verify state when onLoadData first time returns success data`()  {
        // given
        val coinId = "coinId"
        val coinName = "coinName"
        val coinCurrentPrice = "coinCurrentPrice"
        val coinPercentage = "coinPercentage"
        val nativeCurrencySymbol = "nativeCurrencySymbol"
        val news = listOf(DomainFixtures.getNewsData())
        val currencyInfo = DomainFixtures.getCryptocurrencyInfo()
        every { mockCryptocurrencyDetailFragmentArgs.coinId } returns coinId
        every { mockCryptocurrencyDetailFragmentArgs.coinName } returns coinName
        every { mockCryptocurrencyDetailFragmentArgs.coinCurrentPrice } returns coinCurrentPrice
        every { mockCryptocurrencyDetailFragmentArgs.coinPercentage } returns coinPercentage
        every { mockCryptocurrencyDetailFragmentArgs.nativeCurrencySymbol } returns nativeCurrencySymbol
        val nativeCurrency = DomainFixtures.getNativeCurrency()
        coEvery { mockGetUserNativeCurrencyUseCase.execute() } returns
                GetUserNativeCurrencyUseCase.Result.Success(nativeCurrency)
        coEvery { mockCheckIfCryptocurrencyExistsInWatchlistUseCase.execute(coinId) } returns
                CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Success(true)
        coEvery { mockCheckIfCryptocurrencyExistsInWatchlistUseCase.execute(coinId) } returns
                CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Success(true)
        coEvery { mockGetCryptocurrencyChartDataUseCase.execute(any(),any(),any()) } returns
                GetCryptocurrencyChartDataUseCase.Result.Success(any())
        coEvery { mockGetCryptocurrencyInfoUseCase.execute(coinId) } returns
                GetCryptocurrencyInfoUseCase.Result.Success(currencyInfo)
        coEvery { mockGetNewsByCryptocurrencyUseCase.execute(coinId) } returns
                GetNewsByCryptocurrencyUseCase.Result.Success(news)



        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo ViewState(
            isChartDataLoading = false,
            chartDataError = true,
            isCurrencyInfoLoading = false,
            currencyInfoError = false,
            currency = currencyInfo,
            isCurrencyNewsLoading = false,
            currencyNewsError = false,
            currencyNews = news,
            cryptocurrencyExists = true
        )
    }


    @Test
    fun `verify state when onLoadData first time returns empty data`()  {
        // given
        val coinId = "coinId"
        val coinName = "coinName"
        val coinCurrentPrice = "coinCurrentPrice"
        val coinPercentage = "coinPercentage"
        val nativeCurrencySymbol = "nativeCurrencySymbol"
        every { mockCryptocurrencyDetailFragmentArgs.coinId } returns coinId
        every { mockCryptocurrencyDetailFragmentArgs.coinName } returns coinName
        every { mockCryptocurrencyDetailFragmentArgs.coinCurrentPrice } returns coinCurrentPrice
        every { mockCryptocurrencyDetailFragmentArgs.coinPercentage } returns coinPercentage
        every { mockCryptocurrencyDetailFragmentArgs.nativeCurrencySymbol } returns nativeCurrencySymbol
        val nativeCurrency = DomainFixtures.getNativeCurrency()
        coEvery { mockGetUserNativeCurrencyUseCase.execute() } returns
                GetUserNativeCurrencyUseCase.Result.Success(nativeCurrency)
        coEvery { mockCheckIfCryptocurrencyExistsInWatchlistUseCase.execute(coinId) } returns
                CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Error(Exception())

        coEvery { mockCheckIfCryptocurrencyExistsInWatchlistUseCase.execute(coinId) } returns
                CheckIfCryptocurrencyExistsInWatchlistUseCase.Result.Error(Exception())

        coEvery { mockGetCryptocurrencyChartDataUseCase.execute(any(),any(),any()) } returns
                GetCryptocurrencyChartDataUseCase.Result.Error(Exception())

        coEvery { mockGetCryptocurrencyInfoUseCase.execute(coinId) } returns
                GetCryptocurrencyInfoUseCase.Result.Error(Exception())

        coEvery { mockGetNewsByCryptocurrencyUseCase.execute(coinId) } returns
                GetNewsByCryptocurrencyUseCase.Result.Error(Exception())

        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo ViewState(
            isChartDataLoading=false,
            isCurrencyInfoLoading=false,
            isCurrencyNewsLoading=false,
            chartDataError=true,
            currencyInfoError=true,
            currencyNewsError=true,
            cryptocurrencyExists=false,
            coinLineData=null,
            currency=null,
            currencyNews= listOf()
        )
    }
}