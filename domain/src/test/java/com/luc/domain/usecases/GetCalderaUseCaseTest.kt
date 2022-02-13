package com.luc.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.luc.domain.FakeDomainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetCalderaUseCaseTest {

    private lateinit var getCalderaUseCase: GetCalderaUseCase
    private lateinit var fakeDomainRepository: FakeDomainRepository

    @Before
    fun setUp() {
        fakeDomainRepository = FakeDomainRepository()
        getCalderaUseCase = GetCalderaUseCase(fakeDomainRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `precioService should have only two decimals`() {
        runBlockingTest {
            getCalderaUseCase.getCalderas().forEach { caldera ->
                caldera.repuestos.forEach {
                    assertThat(it.precioService.substringAfter(",")).hasLength(2)
                }
            }
        }
    }
}