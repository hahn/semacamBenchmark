package id.husna.imagebenchmarkbase.helpers

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *  Copyright © 2020 Global Digital Niaga, PT. All rights reserved.
 *
 */
sealed class ViewState {
    object LOADING: ViewState()
    object SUCCESS: ViewState()
    data class ERROR(val msg: String? = null): ViewState()
}