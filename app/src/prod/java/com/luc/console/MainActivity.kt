package com.luc.console

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import com.luc.console.databinding.ActivityMainBinding
import com.luc.console.databinding.DrawerHeaderBinding
import com.luc.common.model.Settings
import com.luc.console.utils.lerp
import com.luc.presentation.viewmodel.DomainViewModel
import com.luc.presentation.viewmodel.MainActivityViewModel
import com.luc.presentation.viewmodel.UPDATE_REQUEST_CODE
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var binding: ActivityMainBinding

    private val domainViewModel: DomainViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by viewModel { parametersOf(this) }

    private var settings: Settings? = null

    private var updateTotalBytes: Int? = null

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.isDownloaded.observe(this) {
            if (it) {
                popupSnackbarForCompleteUpdate()
            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
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
        val drawer = DrawerHeaderBinding.bind(headerView)
        drawer.imageUrl =
            "https://play-lh.googleusercontent.com/TfjksNHP5flgSukqCRhv_lz0_c-bkEqcRxJKyjZjZIBcwCZ2H9gJm0HlIKp9G7K87k5M"
        drawer.versionName.text = "V${BuildConfig.VERSION_NAME}"

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

        mainActivityViewModel.isDownloaded.observe(this) {
            if (it) {
                binding.updateProgress.hide()
                popupSnackbarForCompleteUpdate()
            }
        }

        mainActivityViewModel.updateAvailable.observe(this) {
            if (it) binding.newVersion.visibility = View.VISIBLE
            else binding.newVersion.visibility = View.INVISIBLE

        }

        mainActivityViewModel.totalBytesToDownload.observe(this) {
            updateTotalBytes = it.toInt()
            if (!binding.updateProgress.isShown) {
                binding.updateProgress.isIndeterminate = true
                binding.updateProgress.show()

            }
        }

        mainActivityViewModel.bytesDownloaded.observe(this) {
            updateTotalBytes?.let { updateTotalBytes ->
                binding.updateProgress.isIndeterminate = false
                val progress = (it.toFloat() / updateTotalBytes.toFloat()) * 100
                binding.updateProgress.progress = progress.toInt()
            }
        }

        mainActivityViewModel.updateInProgress.observe(this) {
            if (it && binding.newVersion.isEnabled) binding.newVersion.isEnabled = false
        }

        binding.newVersion.setOnClickListener { mainActivityViewModel.startUpdate(this) }

        resultLauncher
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
                        val imm =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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

    // Displays the snackbar notification and call to action.
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.contentContainer),
            getString(R.string.update_ready),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("INSTALAR") { mainActivityViewModel.completeUpdate() }
            anchorView = binding.anchorView
            show()
        }
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

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == UPDATE_REQUEST_CODE)
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.e("MY_APP", "Update flow failed! Result code: ${result.resultCode}")
                }
        }
}