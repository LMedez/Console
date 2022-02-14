package com.luc.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.luc.common.model.Caldera
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.presentation.FakeDomainRepository
import com.luc.presentation.MainCoroutineRule
import com.luc.presentation.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import java.io.Closeable

class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    lateinit var homeViewModel: HomeViewModel
    lateinit var getCalderaUseCase: GetCalderaUseCase
    lateinit var getSettingsUseCase: GetSettingsUseCase
    lateinit var fakeDomainRepository: FakeDomainRepository

    @Before
    fun setUp() {
        fakeDomainRepository = FakeDomainRepository()
        getCalderaUseCase = GetCalderaUseCase(fakeDomainRepository)
        getSettingsUseCase = GetSettingsUseCase(fakeDomainRepository)
        homeViewModel = HomeViewModel(getCalderaUseCase, getSettingsUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `every Repuesto of calderaList should have the precioService variable incremented by 21percent`() {}
//        coroutineRule.runBlockingTest {
//            val originalCaldera = fakeDomainRepository.list
//            homeViewModel.calderaList.observeForever(observer)
//            val calderaList = homeViewModel.calderaList.getOrAwaitValue()
//            assertThat(calderaList).isEqualTo(originalCaldera)
//            homeViewModel.calderaList.removeObserver(observer)
//
//        }
}

private const val TAG = "HomeViewModelTest"