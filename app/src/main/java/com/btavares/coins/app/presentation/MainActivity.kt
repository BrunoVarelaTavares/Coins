package com.btavares.coins.app.presentation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.btavares.coins.R
import com.btavares.library_base.presentation.activity.BaseActivity
import com.btavares.library_base.presentation.navigation.NavManager
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance


class MainActivity: BaseActivity(R.layout.activity_main) {

    private val navigationController get() = navigationFragment.findNavController()

    private  val navigationManager: NavManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        AndroidThreeTen.init(this)
        // initialize nav controller
        bottomNavigationButtons.setupWithNavController(navigationController)

        navigationManager.setOnNavEvent {
            navigationController.navigate(it)
        }

    }

}