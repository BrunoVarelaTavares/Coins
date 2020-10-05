package com.btavares.feature_home.presentation.news

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.btavares.feature_home.R
import com.btavares.feature_home.presentation.home.recyclerview.NewsAdapter
import com.btavares.library_base.presentation.extension.observe
import com.btavares.library_base.presentation.extension.setOnDebouncedClickListener
import com.btavares.library_base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.snipped_top_news_fragment.*
import org.kodein.di.generic.instance

class NewsFragment : InjectionFragment(R.layout.fragment_news){

    private val viewModel: NewsViewModel by instance()

    private val newsAdapter: NewsAdapter by instance()

    private val stateObserver = Observer<NewsViewModel.ViewState> {
        progressBar.visible = it.isLoading
        newsFragmentErrorLayout.visible = it.isError
        newsAdapter.mNewsData = it.mNews
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  context = requireContext()

        newsAdapter.setOnDebouncedClickListener {
            viewModel.navigateToNewsDetails(context,it.url)
        }

        newsBackArrow.setOnDebouncedClickListener{
            viewModel.navigateBackToHome()
        }

        newsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = newsAdapter
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }

}