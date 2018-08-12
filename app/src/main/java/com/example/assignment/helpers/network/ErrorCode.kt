package com.example.assignment.helpers.network

import com.example.assignment.helpers.AppApplication
import com.example.assignment.i002.R

enum class ErrorCode(private val errorCode: Int) {
    ERROR_NETWORK_UNAVAILABLE(R.string.error_network_unavailable),
    ERROR_HOST_UNAVAILABLE(R.string.error_host_unavailable),
    ERROR_HOST_FAULT(R.string.error_host_fault),
    ERROR_HOST_TIMEOUT(R.string.error_host_fault),
    ERROR_RESPONSE_DATA_ARE_NULL(R.string.error_host_fault),
    ERROR_RESPONSE_CORRUPTED_DATA(R.string.error_response_corrupted_data),
    ERROR_INTERNAL(R.string.error_internal),
    ERROR_IGNORE(R.string.error_ignore);

    fun getDescriptionOfError() = AppApplication.getAppContext().getString(this.errorCode)!!

}
