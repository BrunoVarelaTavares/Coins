package com.btavares.feature_home.presentation.registration

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.btavares.feature_home.R
import com.btavares.feature_home.presentation.home.HomeViewModel
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_registration.*


import org.kodein.di.generic.instance

class RegistrationFragment : InjectionFragment(R.layout.fragment_registration){

    private val viewModel : RegistrationViewModel by instance()


    private val stateObserver = Observer<RegistrationViewModel.ViewState> {
        registrationProgressBar.visible = it.isLoading
        tvInsertUserMessageError.visible = it.isError

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val buttons = activity?.findViewById<BottomNavigationView>(com.
                                            btavares.coins.R.id.bottomNavigationButtons)
       buttons?.visibility = View.GONE


        btnSaveUser.setOnDebouncedClickListener {
            if (tIUserFullName.text.toString().isNotEmpty()
                    && tIUserEmail.text.toString().isNotEmpty()) {
                registrationProgressBar.visibility = View.VISIBLE
                val userFullName = tIUserFullName.text.toString()

                val userEmail = tIUserEmail.text.toString()
                viewModel.saveUser(buttons,userFullName,userEmail)
            }

            if (tIUserFullName.text.toString().isEmpty())
                tIUserFullName.error = getText(R.string.empty_username_error_message)

            if (tIUserEmail.text.toString().isEmpty())
                tIUserEmail.error = getText(R.string.empty_email_error_message)

        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()

    }



}