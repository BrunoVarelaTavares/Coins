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

class InsertUserUseCaseTest {

    @MockK
    internal lateinit var mockHomeRepository: HomeRepositoryImpl

    private lateinit var insertUserUseCase: InsertUserUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        insertUserUseCase = InsertUserUseCase(mockHomeRepository)
    }

    @Test
    fun `return user insert success`() {
        //given
        val userId :Long = 20
        coEvery { mockHomeRepository.insertUser(any(),any()) } returns userId

        //when
        val result = runBlocking { insertUserUseCase.execute(any(), any()) }

        //then
        result shouldBeEqualTo InsertUserUseCase.Result.Success(true)

    }

    @Test
    fun `return error when inserting user throws an exception`() {
        //given
        val exception = UnknownHostException()
        coEvery { mockHomeRepository.insertUser(any(),any()) } throws  exception

        //when
        val result = runBlocking { insertUserUseCase.execute(any(), any()) }

        //then
        result shouldBeEqualTo InsertUserUseCase.Result.Error(exception)

    }


}