package com.luc.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.presentation.FakeDomainRepository
import com.luc.presentation.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

class DomainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    lateinit var domainViewModel: DomainViewModel
    lateinit var getCalderaUseCase: GetCalderaUseCase
    lateinit var getSettingsUseCase: GetSettingsUseCase
    lateinit var fakeDomainRepository: FakeDomainRepository

    @Before
    fun setUp() {
        fakeDomainRepository = FakeDomainRepository()
        getCalderaUseCase = GetCalderaUseCase(fakeDomainRepository)
        getSettingsUseCase = GetSettingsUseCase(fakeDomainRepository)
        domainViewModel = DomainViewModel(getCalderaUseCase, getSettingsUseCase)
    }
}

private const val TAG = "HomeViewModelTest"