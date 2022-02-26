package com.luc.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.luc.domain.FakeDomainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCalderaUseCaseTest {

    private lateinit var getCalderaUseCase: GetCalderaUseCase
    private lateinit var fakeDomainRepository: FakeDomainRepository

    @Before
    fun setUp() {
        fakeDomainRepository = FakeDomainRepository()
        getCalderaUseCase = GetCalderaUseCase(fakeDomainRepository)
    }

    @Test
    fun `precioService should have only two decimals`() {
        runBlockingTest {
            getCalderaUseCase.getRepuestos().forEach { repuesto ->
                assertThat(repuesto.precioService.toString().indexOf(".") - 1).isLessThan(3)
            }
        }
    }
}