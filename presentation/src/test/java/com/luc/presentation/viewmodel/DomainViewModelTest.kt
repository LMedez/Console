package com.luc.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.luc.common.model.Settings
import com.luc.domain.email.Mailto
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.domain.usecases.SendEmailUseCase
import com.luc.presentation.FakeDomainRepository
import com.luc.presentation.MainCoroutineRule
import com.luc.presentation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DomainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    lateinit var domainViewModel: DomainViewModel
    lateinit var getCalderaUseCase: GetCalderaUseCase
    lateinit var getSettingsUseCase: GetSettingsUseCase
    lateinit var sendEmailUseCase: SendEmailUseCase
    lateinit var fakeDomainRepository: FakeDomainRepository

    @Before
    fun setUp() {
        fakeDomainRepository = FakeDomainRepository()
        getCalderaUseCase = GetCalderaUseCase(fakeDomainRepository)
        getSettingsUseCase = GetSettingsUseCase(fakeDomainRepository)
        sendEmailUseCase = SendEmailUseCase(Mailto())
        domainViewModel = DomainViewModel(getCalderaUseCase, getSettingsUseCase,sendEmailUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `filter the repuesto list with the given id, list size should be 3`() = runBlockingTest {
        domainViewModel._repuestoList.value = getCalderaUseCase.getRepuestos()

        val repuestoList = domainViewModel.repuestoList("123456").getOrAwaitValue()

        assertThat(repuestoList.size).isEqualTo(3)
    }

}

private const val TAG = "HomeViewModelTest"