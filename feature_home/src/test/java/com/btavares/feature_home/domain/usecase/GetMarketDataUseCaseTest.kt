package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.data.repository.HomeRepositoryImpl
import com.btavares.feature_home.domain.DomainFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class GetMarketDataUseCaseTest {

    @MockK
    internal lateinit var homeRepository: HomeRepositoryImpl

    private lateinit var getMarketDataUseCase: GetMarketDataUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getMarketDataUseCase = GetMarketDataUseCase(homeRepository)
    }

    @Test
    fun `return list of marketData`() {
        //given
        val marketDataList = listOf(DomainFixtures.getMarketData(),DomainFixtures.getMarketData(),DomainFixtures.getMarketData())
        coEvery { homeRepository.getMarketDataAsync(any(),any()) } returns marketDataList

        //when
        val result = runBlocking { getMarketDataUseCase.execute(any(), any()) }

        //then
        result shouldBeEqualTo GetMarketDataUseCase.Result.Success(marketDataList)

    }

    @Test
    fun `return error when throws an exception`() {
        //Given
        val exception = UnknownHostException()
        coEvery { homeRepository.getMarketDataAsync(any(),any()) } throws  exception

        //When
        val result = runBlocking { getMarketDataUseCase.execute(any(), any()) }

        //Then
        result shouldBeEqualTo GetMarketDataUseCase.Result.Error(exception)

    }

}