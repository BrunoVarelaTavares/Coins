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

class GetLatestNewsUseCaseTest {

    @MockK
    internal lateinit var homeRepository: HomeRepositoryImpl

    private lateinit var getLatestNewsUseCase: GetLatestNewsUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getLatestNewsUseCase = GetLatestNewsUseCase(homeRepository)
    }

    @Test
    fun `return list of news when call cryptocurrencies latest news`() {
        //given
        val newsList = listOf(
            DomainFixtures.getNewsData(),
            DomainFixtures.getNewsData())
        coEvery { homeRepository.getCryptocurrenciesLatestNewsAsync() } returns newsList

        //when
        val result = runBlocking { getLatestNewsUseCase.execute() }

        //then
        result shouldBeEqualTo GetLatestNewsUseCase.Result.Success(newsList)

    }

    @Test
    fun `return error when call cryptocurrencies latest news throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { homeRepository.getCryptocurrenciesLatestNewsAsync() } throws  exception

        //when
        val result = runBlocking { getLatestNewsUseCase.execute() }

        //then
        result shouldBeEqualTo GetLatestNewsUseCase.Result.Error(exception)

    }


}