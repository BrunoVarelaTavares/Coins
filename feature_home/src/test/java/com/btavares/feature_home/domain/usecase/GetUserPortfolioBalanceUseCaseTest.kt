package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.data.repository.HomeRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class GetUserPortfolioBalanceUseCaseTest {

    @MockK
    internal lateinit var homeRepository: HomeRepositoryImpl

    private lateinit var getUserPortfolioBalanceUseCase : GetUserPortfolioBalanceUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getUserPortfolioBalanceUseCase = GetUserPortfolioBalanceUseCase(homeRepository)
    }

    @Test
    fun `return user balance`() {
        //given
        val userBalance = 15.66
        coEvery { homeRepository.getUserBalance() } returns userBalance

        //when
        val result = runBlocking { getUserPortfolioBalanceUseCase.execute() }

        //then
        result shouldBeEqualTo GetUserPortfolioBalanceUseCase.Result.Success(userBalance)

    }

    @Test
    fun `return error when requesting user balance throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { homeRepository.getUserBalance() } throws  exception

        //when
        val result = runBlocking { getUserPortfolioBalanceUseCase.execute() }

        //then
        result shouldBeEqualTo GetUserPortfolioBalanceUseCase.Result.Error(exception)

    }

}