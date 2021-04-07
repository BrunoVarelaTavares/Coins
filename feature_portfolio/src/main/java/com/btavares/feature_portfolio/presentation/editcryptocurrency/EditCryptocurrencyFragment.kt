package com.btavares.feature_portfolio.presentation.editcryptocurrency

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_portfolio.R
import com.btavares.feature_portfolio.presentation.portfolio.PortfolioViewModel
import com.btavares.feature_portfolio.presentation.portfolio.recyclerview.PortfolioCryptocurrencyAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_edit_cryptocurrency.*
import kotlinx.android.synthetic.main.fragment_portfolio.*
import kotlinx.android.synthetic.main.snipped_edit_cryptocurrency_fragment.*
import org.kodein.di.generic.instance

internal class EditCryptocurrencyFragment : InjectionFragment(R.layout.fragment_edit_cryptocurrency){

    private val viewModel: EditCryptocurrencyViewModel by instance()

    private val stateObserver = Observer<EditCryptocurrencyViewModel.ViewState> {



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewTitle.text = viewModel.getTitle()
        editTextCurrencyValue.setText(viewModel.getNativeCurrencyPortfolioValue())

        editCryptoBackArrow.setOnDebouncedClickListener {
            viewModel.navigateBackToPortfolioFragment()
        }

        buttonSave.setOnDebouncedClickListener {
            val currencyValue = editTextCurrencyValue.text.toString().toDouble()
            viewModel.saveCurrencyValue(currencyValue)
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}
