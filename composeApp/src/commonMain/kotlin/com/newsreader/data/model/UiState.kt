package com.newsreader.data.model

/**
 * Sealed class to represent the three possible UI states for any data-loading screen.
 * - Loading : data is being fetched
 * - Success : data fetched successfully
 * - Error   : something went wrong (carries a human-readable message)
 */
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class  Success<T>(val data: T) : UiState<T>()
    data class  Error(val message: String) : UiState<Nothing>()
}
