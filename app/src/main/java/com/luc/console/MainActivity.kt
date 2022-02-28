package com.luc.console

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.luc.console.databinding.ActivityMainBinding
import com.luc.console.databinding.DrawerHeaderBinding
import com.luc.common.model.Settings
import com.luc.presentation.viewmodel.DomainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var binding: ActivityMainBinding

    private val domainViewModel: DomainViewModel by viewModel()

    private var settings: Settings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.domainViewModel = domainViewModel

        val toolbar: MaterialToolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        )

        toggle.isDrawerIndicatorEnabled = true

        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)

        /* Load image of header drawer*/
        val headerView = binding.navigationView.getHeaderView(0)
        DrawerHeaderBinding.bind(headerView).imageUrl =
            "https://play-lh.googleusercontent.com/TfjksNHP5flgSukqCRhv_lz0_c-bkEqcRxJKyjZjZIBcwCZ2H9gJm0HlIKp9G7K87k5M"


        domainViewModel.settings.observe(this) {
            settings = it

            val switchIva = getActionView(R.id.iva)
            val switchGain = getActionView(R.id.gainValue)

            switchIva.isChecked = it.applyIva
            switchGain.isChecked = it.applyGain
            switchIva.setOnCheckedChangeListener { _, isChecked ->
                domainViewModel.updateSettings(it.copy(applyIva = isChecked))
            }

            switchGain.setOnCheckedChangeListener { _, isChecked ->
                domainViewModel.updateSettings(it.copy(applyGain = isChecked))
            }
            setUpMenuEditText()
        }
    }

    @SuppressLint("WrongConstant")
    fun setUpMenuEditText() {
        val editText =
            binding.navigationView.menu.findItem(R.id.dolarValue).actionView as AppCompatEditText
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.maxLines = 1
        editText.filters += InputFilter.LengthFilter(3)
        editText.setText(settings?.dolarValue.toString())

        editText.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! == 3 && text.toString() != settings?.dolarValue.toString()) {
                settings?.let {
                    drawerLayout.closeDrawer(Gravity.START)
                    domainViewModel.updateSettings(
                        it.copy(
                            dolarValue = text.toString().toInt()
                        )
                    )

                    Toast.makeText(this, "Dolar actualizado", Toast.LENGTH_LONG).show()

                    // Only runs if there is a view that is currently focused
                    this.currentFocus?.let { view ->
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        }
    }

    fun getActionView(menuId: Int): SwitchMaterial {
        val menuItem = binding.navigationView.menu.findItem(menuId)
        return menuItem.actionView as SwitchMaterial
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)

    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(menuItem)) {
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }
}