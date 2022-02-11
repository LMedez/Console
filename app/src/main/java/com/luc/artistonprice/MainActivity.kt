package com.luc.artistonprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.luc.common.entities.CalderaEntity
import com.luc.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel.getCalderas().observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}