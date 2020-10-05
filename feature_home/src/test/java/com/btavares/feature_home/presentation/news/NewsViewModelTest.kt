package com.btavares.feature_home.presentation.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.btavares.feature_home.domain.DomainFixtures
import com.btavares.feature_home.domain.usecase.GetLatestNewsUseCase
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.feature_home.presentation.news.NewsViewModel.ViewState
import com.btavares.library_test_utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NewsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    internal lateinit var mockNavManager: NavManager

    @MockK
    internal lateinit var mockGetLatestNewsUseCase: GetLatestNewsUseCase

    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = NewsViewModel(
            mockNavManager,
            mockGetLatestNewsUseCase
        )
    }


    @Test
    fun `execute getLatestNewsUseCase`()  {
        // when
        viewModel.loadData()

        // then
        coVerify { mockGetLatestNewsUseCase.execute() }
    }


    @Test
    fun `verify state when GetLatestNewsUseCase returns list of news`() {
        // given
        val news = DomainFixtures.getNewsData()
        val newsList = listOf(news,news,news)
        coEvery { mockGetLatestNewsUseCase.execute() } returns GetLatestNewsUseCase.Result.Success(newsList)

        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo ViewState(
            isLoading = false,
            isError = false,
            mNews = newsList

        )
    }

    @Test
    fun `verify state when GetLatestNewsUseCase returns empty list`() {
        // given
        coEvery { mockGetLatestNewsUseCase.execute() } returns GetLatestNewsUseCase.Result.Success(emptyList())

        // when
        viewModel.loadData()

        // then
        viewModel.stateLiveData.value shouldBeEqualTo ViewState(
            isLoading = false,
            isError = true,
            mNews = listOf())
    }


    @Test
    fun `navigate back to Home Fragment`() {
        // given
        val navDirections = NewsFragmentDirections.actionNewsGoBackToHomeFragment()

        // when
        viewModel.navigateBackToHome()

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }


}