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

class GetCryptocurrencyMarketDataUseCaseTest {

    @MockK
    internal lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl

    private lateinit var getCryptocurrencyMarketDataUse: GetCryptocurrencyMarketDataUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getCryptocurrencyMarketDataUse = GetCryptocurrencyMarketDataUseCase(coinDetailRepositoryImpl)
    }

    @Test
    fun `return list of CryptocurrencyMarketData`() {
        //given
        val marketDataList = mutableListOf(DomainFixtures.getCryptocurrencyMarketData(),
            DomainFixtures.getCryptocurrencyMarketData(),DomainFixtures.getCryptocurrencyMarketData())
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketData(any()) } returns marketDataList

        //when
        val result = runBlocking { getCryptocurrencyMarketDataUse.execute(any()) }

        //then
        result shouldBeEqualTo GetCryptocurrencyMarketDataUseCase.Result.Success(marketDataList)

    }

    @Test
    fun `return error when call getCryptocurrencyMarketData throws an exception`() {
        //Given
        val exception = UnknownHostException()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyMarketData(any()) } throws  exception

        //When
        val result = runBlocking { getCryptocurrencyMarketDataUse.execute(any()) }

        //Then
        result shouldBeEqualTo GetCryptocurrencyMarketDataUseCase.Result.Error(exception)

    }
}