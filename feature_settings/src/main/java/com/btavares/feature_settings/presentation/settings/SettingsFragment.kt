package com.btavares.feature_settings.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.btavares.feature_settings.R
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.generic.instance

class SettingsFragment : InjectionFragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by instance()


    private val stateObserver = Observer<SettingsViewModel.ViewState> {
        settingsProgressBar.visible = it.isLoading
        collapsingToolbarSettings.title = it.user?.name
        tvUserEmail.text = it.user?.email
        tvCurrencyCode.text = it.currency?.currencyCode
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()


        rlNativeCurrency.setOnDebouncedClickListener {
            val singleItems = viewModel.getCurrencies()
            val checkedItem = viewModel.getNativeCurrencyIndex()

            MaterialAlertDialogBuilder(context)
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                }
                .setPositiveButton(resources.getString(R.string.label_ok)) { dialog, which ->
                    val position = (dialog as AlertDialog).listView.checkedItemPosition
                    if (position != checkedItem)
                        viewModel.updateNativeCurrency(singleItems[position])
                }
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                }
                .show()
        }

        rlUserSettings.setOnDebouncedClickListener {
            viewModel.navigateToProfileFragment(collapsingToolbarSettings.title.toString(),
                                                tvUserEmail.text.toString())
        }



        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}