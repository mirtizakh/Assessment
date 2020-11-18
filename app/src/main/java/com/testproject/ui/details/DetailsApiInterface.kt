package com.testproject.ui.details


import com.testproject.data.network.api_call.Resource
import com.testproject.data.network.response.response_models.ItemsResponse

interface DetailsApiInterface
{
    suspend  fun  items(): Resource<ItemsResponse>
}