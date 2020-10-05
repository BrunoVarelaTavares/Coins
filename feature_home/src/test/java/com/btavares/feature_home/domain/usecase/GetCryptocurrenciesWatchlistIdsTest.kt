package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.data.repository.HomeRepositoryImpl
import com.btavares.feature_home.domain.DomainFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class GetCryptocurrenciesWatchlistIdsTest {

    @MockK
    internal lateinit var homeRepository: HomeRepositoryImpl

    private lateinit var getCryptocurrenciesWatchlistIds: GetCryptocurrenciesWatchlistIds

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getCryptocurrenciesWatchlistIds = GetCryptocurrenciesWatchlistIds(homeRepository)
    }

    @Test
    fun `return cryptocurrencies watchlist ids when call getCryptocurrenciesWatchlistIds`() {
        //given
        val coinsIds =  DomainFixtures.getCryptocurrenciesWatchlistIds()
        coEvery { homeRepository.getCryptocurrenciesWatchlistIds() } returns coinsIds

        //when
        val result = runBlocking { getCryptocurrenciesWatchlistIds.execute() }

        //then
        result shouldBeEqualTo GetCryptocurrenciesWatchlistIds.Result.Success(coinsIds)

    }

    @Test
    fun `return error when call getCryptocurrenciesWatchlistIds throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { homeRepository.getCryptocurrenciesWatchlistIds() } throws  exception

        //when
        val result = runBlocking { getCryptocurrenciesWatchlistIds.execute() }

        //then
        result shouldBeEqualTo GetCryptocurrenciesWatchlistIds.Result.Error(exception)

    }




}