package com.luc.common

import com.google.common.truth.Truth.assertThat
import com.luc.common.utils.addPercent
import com.luc.common.utils.round
import org.junit.Test


class UtilsTest {
    @Test
    fun `decimals should be only two decimals`() {
        val decimal = 12.565878987.round()
        assertThat(decimal).isEqualTo(12.57)
    }

    @Test
    fun `should be apply the given percent to a double and map to two decimals`() {
        val percent = 21
        assertThat(addPercent(126.78, percent)).isEqualTo(153.40)
    }
}