package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.data.repository.CoinDetailRepositoryImpl
import com.btavares.feature_coin_detail.domain.DomainFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class GetCryptocurrencyChartDataUseCaseTest {

    @MockK
    internal lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl

    private lateinit var getCryptocurrencyChartDataUseCase: GetCryptocurrencyChartDataUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getCryptocurrencyChartDataUseCase = GetCryptocurrencyChartDataUseCase(coinDetailRepositoryImpl)
    }

    @Test
    fun `return cryptocurrency market chart data`() {
        //given
        val marketChartData = DomainFixtures.getCryptocurrencyMarketChartDomainModel()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketChart(any(),any(), any()) } returns marketChartData

        //when
        val result = runBlocking { getCryptocurrencyChartDataUseCase.execute(any(), any(), any()) }

        //then
        result shouldBeEqualTo GetCryptocurrencyChartDataUseCase.Result.Success(marketChartData)

    }

    @Test
    fun `return error when call getCryptocurrencyMarketChart throws an exception`() {
        //Given
        val exception = UnknownHostException()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketChart(any(),any(),any()) } throws  exception

        //When
        val result = runBlocking { getCryptocurrencyChartDataUseCase.execute(any(), any(), any())  }

        //Then
        result shouldBeEqualTo GetCryptocurrencyChartDataUseCase.Result.Error(exception)

    }


}