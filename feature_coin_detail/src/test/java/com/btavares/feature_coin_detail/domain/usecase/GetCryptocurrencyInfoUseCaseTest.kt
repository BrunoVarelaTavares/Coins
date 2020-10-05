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

class GetCryptocurrencyInfoUseCaseTest {

    @MockK
    internal lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl

    private lateinit var getCryptocurrencyInfoUseCase: GetCryptocurrencyInfoUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getCryptocurrencyInfoUseCase = GetCryptocurrencyInfoUseCase(coinDetailRepositoryImpl)
    }

    @Test
    fun `return cryptocurrency info `() {
        //given
        val cryptoInfo = DomainFixtures.getCryptocurrencyInfo()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyInfoAsync(any()) } returns cryptoInfo

        //when
        val result = runBlocking { getCryptocurrencyInfoUseCase.execute(any()) }

        //then
        result shouldBeEqualTo GetCryptocurrencyInfoUseCase.Result.Success(cryptoInfo)

    }

    @Test
    fun `return error when call getCryptocurrencyInfoAsync throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { coinDetailRepositoryImpl.getCryptocurrencyInfoAsync(any()) }  throws  exception

        //when
        val result = runBlocking { getCryptocurrencyInfoUseCase.execute(any()) }

        //then
        result shouldBeEqualTo GetCryptocurrencyInfoUseCase.Result.Error(exception)

    }

}