package com.testproject.data.network.response

enum class ResponseCodes(val code:Int)
{
    SUCCESS_CODE_200(200),
    ERROR_CODE_401(401),
    ERROR_CODE_404(404),
    ERROR_CODE_400(400),
    ERROR_CODE_500(500)
}

enum class ResponseMessages(val message:String)
{
    SOME_THING_WENT_WRONG("Something went wrong please try again later"),
    SESSION_TIMEOUT("Sorry, you didn’t get a response . Please try again"),
    NETWORK_ERROR("Please check your internet connection")
}