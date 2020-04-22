package id.husna.benchglide.helpers

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
sealed class ViewState {
    object LOADING: ViewState()
    object SUCCESS: ViewState()
    data class ERROR(val msg: String? = null): ViewState()
}