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

class GetNewsByCryptocurrencyUseCaseTest {

    @MockK
    internal lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl

    private lateinit var getNewsByCryptocurrencyUseCase: GetNewsByCryptocurrencyUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getNewsByCryptocurrencyUseCase = GetNewsByCryptocurrencyUseCase(coinDetailRepositoryImpl)
    }

    @Test
    fun `return list of news by cryptocurrency`() {
        //given
        val newsList = listOf(
            DomainFixtures.getNewsData(),
            DomainFixtures.getNewsData())
        coEvery { coinDetailRepositoryImpl.getNewsByCryptocurrencyAsync(any()) } returns newsList

        //when
        val result = runBlocking { getNewsByCryptocurrencyUseCase.execute(any()) }

        //then
        result shouldBeEqualTo GetNewsByCryptocurrencyUseCase.Result.Success(newsList)

    }

    @Test
    fun `return error when call getNewsByCryptocurrencyAsync throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { coinDetailRepositoryImpl.getNewsByCryptocurrencyAsync(any()) } throws  exception

        //when
        val result = runBlocking { getNewsByCryptocurrencyUseCase.execute(any()) }

        //then
        result shouldBeEqualTo GetNewsByCryptocurrencyUseCase.Result.Error(exception)

    }



}