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

class GetNewsUseCaseTest {

    @MockK
    internal lateinit var homeRepository: HomeRepositoryImpl

    private lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        getNewsUseCase = GetNewsUseCase(homeRepository)
    }

    @Test
    fun `return list of news when call cryptocurrencies top news`() {
        //given
        val newsList = listOf(
            DomainFixtures.getNewsData(),
            DomainFixtures.getNewsData())
        coEvery { homeRepository.getCryptocurrenciesTopNewsAsync() } returns newsList

        //when
        val result = runBlocking { getNewsUseCase.execute() }

        //then
        result shouldBeEqualTo GetNewsUseCase.Result.Success(newsList)

    }

    @Test
    fun `return error when call cryptocurrencies news throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { homeRepository.getCryptocurrenciesTopNewsAsync() } throws  exception

        //when
        val result = runBlocking { getNewsUseCase.execute() }

        //then
        result shouldBeEqualTo GetNewsUseCase.Result.Error(exception)

    }

}