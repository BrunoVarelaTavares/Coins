package com.btavares.feature_home.presentation.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.btavares.feature_home.data.DataFixtures
import com.btavares.feature_home.data.model.toDomainModel
import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_home.domain.usecase.*
import com.btavares.feature_home.presentation.home.HomeFragmentDirections
import com.btavares.feature_home.presentation.home.HomeViewModel
import com.btavares.library_base.presentation.navigation.NavManager
import com.btavares.library_test_utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class RegistrationViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()


    @MockK(relaxed = true)
    internal lateinit var mockNavManager: NavManager

    @MockK
    internal lateinit var mockInsertUserUseCase: InsertUserUseCase

    private lateinit var viewModel: RegistrationViewModel


    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        viewModel = RegistrationViewModel(
            mockNavManager,
            mockInsertUserUseCase
        )

    }

    @Test
    fun `insert user successfully and navigate to news fragment`() {
        // given
        val username = "username"
        val email = "email@gmail.com"
        val navDirections = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment()
        coEvery { mockInsertUserUseCase.execute(username, email) } returns
                InsertUserUseCase.Result.Success(true)

        // when
        viewModel.saveUser(any(),username, email)

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }

    @Test
    fun `verify state when inserting new user `() {
        // given
        val username = "username"
        val email = "email@gmail.com"
        coEvery { mockInsertUserUseCase.execute(username, email) } returns
                InsertUserUseCase.Result.Success(true)
        // when
        viewModel.saveUser(any(),username, email)

        // then
        viewModel.stateLiveData.value shouldBeEqualTo RegistrationViewModel.ViewState(
            isLoading = false,
            isError = false,
            isUserInsertSuccessful = true
        )
    }

    @Test
    fun `verify state when inserting user returns a exception`() {
        // given
        val username = "username"
        val email = "email@gmail.com"
        coEvery { mockInsertUserUseCase.execute(username, email) } returns
                InsertUserUseCase.Result.Error(Exception())
        // when
        viewModel.saveUser(any(),username, email)

        // then
        viewModel.stateLiveData.value shouldBeEqualTo RegistrationViewModel.ViewState(
            isLoading = false,
            isError = true,
            isUserInsertSuccessful = false
        )
    }
}