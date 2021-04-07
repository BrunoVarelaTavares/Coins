package com.btavares.feature_home.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.btavares.feature_home.domain.DomainFixtures
import com.btavares.feature_home.domain.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.btavares.library_test_utils.CoroutineRule
import com.btavares.library_base.presentation.navigation.NavManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()


    @MockK(relaxed = true)
    internal lateinit var mockNavManager: NavManager

    @MockK
    internal lateinit var mockGetMarketDataUseCase : GetMarketDataUseCase

    @MockK
    internal lateinit var mockGetNewsUseCase : GetNewsUseCase

    @MockK
    internal lateinit var mockGetUserNativeCurrencyUseCase : GetUserNativeCurrencyUseCase

    @MockK
    internal lateinit var mockGetUserPortfolioBalanceUseCase : GetUserPortfolioBalanceUseCase

    @MockK
    internal lateinit var mockGetCryptocurrenciesWatchlistIds : GetCryptocurrenciesWatchlistIds

    @MockK
    internal lateinit var mockGetAllMarketDataUseCase: GetAllMarketDataUseCase

    @MockK
    internal lateinit var mockCheckIfUserExistsUseCase: CheckIfUserExistsUseCase

    private lateinit var viewModel: HomeViewModel



    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        viewModel = HomeViewModel(
            mockNavManager,
            mockGetMarketDataUseCase,
            mockGetAllMarketDataUseCase,
            mockGetNewsUseCase,
            mockGetUserNativeCurrencyUseCase,
            mockGetUserPortfolioBalanceUseCase,
            mockGetCryptocurrenciesWatchlistIds,
            mockCheckIfUserExistsUseCase
        )

    }


    @Test
    fun  `execute CheckIfUserExistsUseCase `() {
        //when
        viewModel.loadData()

        //then
        coVerify { mockCheckIfUserExistsUseCase.execute() }

    }

    @Test
    fun `navigate to news fragment`() {
        // given
        val navDirections = HomeFragmentDirections.actionHomeMoreNewsToNews()

        // when
        viewModel.navigateToNewsLayout()

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }


    @Test
    fun `navigate to registration fragment`() {
        // given
        val navDirections = HomeFragmentDirections.actionHomeToRegistrationFragment()

        // when
        viewModel.navigateToRegistrationFragment()

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }

    @Test
    fun `navigate to cryptocurrency details`() {
        // given
        val coinId = "bitcoin"
        val coinName = "Bitcoin"
        val coinCurrentPrice = "123.99"
        val coinPercentage = "33.9%"
        val nativeCurrencySymbol = "€"
        val navDirections = HomeFragmentDirections.actionHomeToCoinMarketDetailFragment(
            coinId,
            coinName,
            coinCurrentPrice,
            coinPercentage,
            nativeCurrencySymbol

        )

        // whenGetAllMarketDataUseCase
        viewModel.navigateToCoinMarketDetailFragment(coinId
            ,coinName, coinCurrentPrice,coinPercentage ,nativeCurrencySymbol)

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }


    @Test
    fun `verify state when GetNewsUseCase returns non-empty list`() {
        // given
        coEvery { mockGetNewsUseCase.execute() } returns GetNewsUseCase.Result.Success(listOf())

        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo null
    }

    @Test
    fun `verify state when onLoadData first time returns empty data`()  {
        // given
        coEvery { mockCheckIfUserExistsUseCase.execute() } returns
                CheckIfUserExistsUseCase.Result.Success(true)

        coEvery { mockGetUserNativeCurrencyUseCase.execute() } returns
                GetUserNativeCurrencyUseCase.Result.Error(Exception())

        coEvery { mockGetUserPortfolioBalanceUseCase.execute() } returns
                GetUserPortfolioBalanceUseCase.Result.Error(Exception())

        coEvery { mockGetCryptocurrenciesWatchlistIds.execute() } returns
                GetCryptocurrenciesWatchlistIds.Result.Error(Exception())

        coEvery { mockGetMarketDataUseCase.execute(any(),any()) } returns
                GetMarketDataUseCase.Result.Error(Exception())

        coEvery { mockGetAllMarketDataUseCase.execute(any()) } returns
                GetAllMarketDataUseCase.Result.Error(Exception())


        coEvery { mockGetNewsUseCase.execute() } returns
                GetNewsUseCase.Result.Error(Exception())



        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo HomeViewModel.ViewState(
            isWatchlistLoading=false,
            isTopMoversLoading=false,
            isNewsLoading=false,
            isWatchlistDataRequestError=true,
            isTopMoversDataRequestError=true,
            isNewsDataRequestError=true,
            currencySymbol="",
            portfolioBalance=0.0,
            cryptocurrencyMarketData= listOf(),
            cryptocurrencyMarketTopMovers= listOf(),
            mNews= listOf()

        )






    }


    @Test
    fun `verify state when onLoadData first time returns success data`()  {
        // given
        val nativeCurrency = DomainFixtures.getNativeCurrency()
        val portfolioBalance = 20.55
        val idsList = DomainFixtures.getCryptocurrenciesWatchlistIds()
        val coinsList = listOf(DomainFixtures.getMarketData())
        val newsList = listOf(DomainFixtures.getNewsData())
        coEvery { mockCheckIfUserExistsUseCase.execute() } returns
                CheckIfUserExistsUseCase.Result.Success(true)

        coEvery { mockGetUserNativeCurrencyUseCase.execute() } returns
                GetUserNativeCurrencyUseCase.Result.Success(nativeCurrency)

        coEvery { mockGetUserPortfolioBalanceUseCase.execute() } returns
                GetUserPortfolioBalanceUseCase.Result.Success(portfolioBalance)

        coEvery { mockGetCryptocurrenciesWatchlistIds.execute() } returns
                GetCryptocurrenciesWatchlistIds.Result.Success(idsList)

        coEvery { mockGetMarketDataUseCase.execute(any(),any()) } returns
                GetMarketDataUseCase.Result.Success(coinsList)

        coEvery { mockGetNewsUseCase.execute() } returns
                GetNewsUseCase.Result.Success(newsList)

        coEvery { mockGetAllMarketDataUseCase.execute(any()) } returns
                GetAllMarketDataUseCase.Result.Success(coinsList)



        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo HomeViewModel.ViewState(
            isWatchlistLoading=false,
            isTopMoversLoading=false,
            isNewsLoading=false,
            isWatchlistDataRequestError=false,
            isTopMoversDataRequestError=false,
            isNewsDataRequestError=false,
            currencySymbol= nativeCurrency.currencySymbol,
            portfolioBalance= portfolioBalance,
            cryptocurrencyMarketData= coinsList,
            cryptocurrencyMarketTopMovers = coinsList,
            mNews= newsList

        )
    }


    @Test
    fun `navigate to registration fragment if user do not exists `(){
        // given
        val navDirections = HomeFragmentDirections.actionHomeToRegistrationFragment()
        coEvery { mockCheckIfUserExistsUseCase.execute() } returns
                CheckIfUserExistsUseCase.Result.Success(false)

        // when
        viewModel.loadData()

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }

}