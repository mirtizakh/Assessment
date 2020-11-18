package com.testproject.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testproject.R
import com.testproject.adapter.ItemsAdapter
import com.testproject.app.AppController
import com.testproject.data.network.response.response_models.Item
import com.testproject.util.AppUtils
import kotlinx.android.synthetic.main.details_fragment.*
import org.kodein.di.generic.instance

class DetailsFragment : Fragment() {

    private val detailsViewModelFactory : DetailsViewModelFactory by AppController.kodein().instance()
    private lateinit var viewModel: DetailsViewModel
    private var listArray: ArrayList<Item>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.details_fragment, container, false)
        viewModel = ViewModelProvider(this,detailsViewModelFactory).get(DetailsViewModel::class.java)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (rvItems.layoutManager as LinearLayoutManager).orientation= RecyclerView.VERTICAL
        rvItems.adapter = ItemsAdapter(listArray)

        if(listArray==null) viewModel.getItems()

        // On success response of login redirecting user to view item screen.
        viewModel.liveDataResponseSuccess.observe(viewLifecycleOwner, { result->
            result?.let {
                ((rvItems.adapter) as ItemsAdapter).setData(result)

                viewModel.liveDataResponseSuccess.value=null }
        })

        // On error 401 , 400, 404 , 500 and timeout
        viewModel.liveDataResponseError.observe(viewLifecycleOwner, { result->
            result?.let {
                AppUtils.showSnackBar(result)
                viewModel.liveDataResponseError.value=null }
        })

        // On network not found showing error
        // If required we can redirect user to an internet error screen
        viewModel.liveDataResponseNetworkError.observe(viewLifecycleOwner, { result->
            result?.let {
                AppUtils.showSnackBar(result)
                viewModel.liveDataResponseNetworkError.value=null }
        })
    }

}