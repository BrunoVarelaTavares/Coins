package com.btavares.feature_settings.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.btavares.feature_settings.R
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.snipped_profile_fragment.*
import org.kodein.di.generic.instance

class ProfileFragment : InjectionFragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by instance()


    private val stateObserver = Observer<ProfileViewModel.ViewState> {
        profileProgressBar.visible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textInputUserName.setText(viewModel.getUserName())
        textInputUserEmail.setText(viewModel.getUserEmail())

        profileBackArrow.setOnDebouncedClickListener {
            viewModel.navigateBackToSettings()
        }

        btnUpdateProfile.setOnDebouncedClickListener {
            profileProgressBar.visible = true
            viewModel.updateUserFields(textInputUserName.text.toString(),textInputUserEmail.text.toString())
        }



        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}