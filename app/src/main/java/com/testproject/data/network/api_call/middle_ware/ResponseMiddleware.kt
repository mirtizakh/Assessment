package com.testproject.data.network.api_call.middle_ware

import com.google.gson.Gson
import com.testproject.data.network.api_call.Resource
import com.testproject.data.network.response.ResponseCodes
import com.testproject.data.network.response.ResponseMessages
import com.testproject.data.network.api_call.api_call.ApiRequest
import com.testproject.data.network.api_call.network_handler.NetworkAvailableInterface
import com.testproject.data.network.response.response_models.ErrorResponse
import com.testproject.ui.dialog.LoaderDialogInterface
import retrofit2.Response
import java.io.IOException

class ResponseMiddleware(
    private var loaderDialogInterface: LoaderDialogInterface,
    private var networkAvailableInterface: NetworkAvailableInterface,
   ) : ApiRequest()
{

    suspend fun <T> networkCall(call: suspend ()-> Response<T>): Resource<T>
     {
              if(networkAvailableInterface.isInternetIsAvailable())
              {
                   loaderDialogInterface.showLoader()
                  var response :Response<T>? = null
                  try {
                      response= apiRequest { call() }
                     }
                      catch (e: IOException)
                      {
                          e.printStackTrace()
                          return exception(ResponseMessages.SESSION_TIMEOUT.message)
                      }
                      catch (e: Exception)
                      {
                          e.printStackTrace()
                          return exception(ResponseMessages.SOME_THING_WENT_WRONG.message)
                      }
                      loaderDialogInterface.hideLoader()
                     return parseApiResponse(response)

              }
              else return Resource.networkError()
     }

    private fun<T> exception (text :String) : Resource<T>
    {
        loaderDialogInterface.hideLoader()
        return Resource.error(text)
    }
     private fun <T> parseApiResponse(response: Response<T>?):Resource<T>
    {
        response?.let {
                  return when(response.code())
                  {
                      ResponseCodes.SUCCESS_CODE_200.code -> Resource.success(data = response.body())
                      // 400 Bad request - any parameter is missing or wrong
                      ResponseCodes.ERROR_CODE_400.code,
                          //404 - resource not found - i.e email not found
                      ResponseCodes.ERROR_CODE_404.code,
                      // Internal Server Error
                      ResponseCodes.ERROR_CODE_500.code ,
                       // Authentication Error
                      ResponseCodes.ERROR_CODE_401.code -> {
                          var errorResponse : ErrorResponse?=null
                          response.errorBody()?.let { responseBody ->
                              errorResponse = Gson().fromJson(response.errorBody()?.charStream(),
                                  ErrorResponse::class.java)
                          }
                          // We can perform different action as well according to status codes if required
                          // Right now no specific action is required, so I am using almost same error return action
                          when {
                              response.code() == ResponseCodes.ERROR_CODE_401.code -> Resource.authError(errorResponse as T)
                              response.code() == ResponseCodes.ERROR_CODE_400.code -> Resource.validationError(errorResponse as T)
                              response.code() == ResponseCodes.ERROR_CODE_404.code -> Resource.notFoundError(errorResponse as T)
                              else -> Resource.error(ResponseMessages.SOME_THING_WENT_WRONG.message)
                          }
                      }
                      else -> Resource.error(ResponseMessages.SOME_THING_WENT_WRONG.message)
                  }

        }?: kotlin.run {
            return Resource.error(ResponseMessages.SOME_THING_WENT_WRONG.message) }
    }
}