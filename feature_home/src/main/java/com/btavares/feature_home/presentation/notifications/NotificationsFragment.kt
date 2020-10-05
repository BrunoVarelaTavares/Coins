package com.btavares.feature_home.presentation.notifications

import android.os.Bundle
import android.view.View
import com.btavares.feature_home.R
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import kotlinx.android.synthetic.main.snipped_top_notifications_fragment.*
import org.kodein.di.generic.instance

class NotificationsFragment : InjectionFragment(R.layout.fragment_notifications){

    private val viewModel : NotificationsViewModel by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ivNotificationsBackArrow.setOnDebouncedClickListener {
            viewModel.navigateBackToHome()
        }
    }

}