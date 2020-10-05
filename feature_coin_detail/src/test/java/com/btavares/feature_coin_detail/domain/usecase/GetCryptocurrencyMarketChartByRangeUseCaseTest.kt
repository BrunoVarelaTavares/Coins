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

class GetCryptocurrencyMarketChartByRangeUseCaseTest {

    @MockK
    internal lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl

    private lateinit var getCryptocurrencyMarketChartByRangeUseCase: GetCryptocurrencyMarketChartByRangeUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getCryptocurrencyMarketChartByRangeUseCase = GetCryptocurrencyMarketChartByRangeUseCase(coinDetailRepositoryImpl)
    }

    @Test
    fun `return cryptocurrency market chart by range data`() {
        //given
        val marketChartData = DomainFixtures.getCryptocurrencyMarketChartDomainModel()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketChartByRange(any(),any(), any(), any()) } returns marketChartData

        //when
        val result = runBlocking { getCryptocurrencyMarketChartByRangeUseCase.execute(any(), any(), any(), any()) }

        //then
        result shouldBeEqualTo GetCryptocurrencyMarketChartByRangeUseCase.Result.Success(marketChartData)

    }

    @Test
    fun `return error when call getCryptocurrencyMarketChartByRange throws an exception`() {
        //Given
        val exception = UnknownHostException()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketChartByRange(any(),any(), any(), any()) } throws  exception

        //When
        val result = runBlocking { getCryptocurrencyMarketChartByRangeUseCase.execute(any(), any(), any(), any())  }

        //Then
        result shouldBeEqualTo GetCryptocurrencyMarketChartByRangeUseCase.Result.Error(exception)

    }


}