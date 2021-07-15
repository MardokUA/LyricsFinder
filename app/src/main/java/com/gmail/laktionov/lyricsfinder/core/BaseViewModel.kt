package com.gmail.laktionov.lyricsfinder.core

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.core.BaseResponse.DataResponse
import com.gmail.laktionov.lyricsfinder.core.BaseResponse.ErrorResponse
import com.gmail.laktionov.lyricsfinder.core.Failure.General.NetworkException
import com.gmail.laktionov.lyricsfinder.core.Failure.General.ServerException
import com.gmail.laktionov.lyricsfinder.ui.UIState
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    protected lateinit var resources: Resources

    /**
     * Converts domain's response to presentation's state.
     */
    //TODO: add localized message
    protected fun <T : Any> BaseResponse<T>.toUiState(): UIState<T> {
        return when (this) {
            is DataResponse -> UIState.Data(data)
            is ErrorResponse -> UIState.Error(
                when (cause) {
                    is NetworkException -> resources.getString(R.string.error_connection)
                    is ServerException -> resources.getString(R.string.error_server_default)
                    else -> resources.getString(R.string.error_default)
                }
            )
        }
    }
}