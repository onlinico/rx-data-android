/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data

import co.onlini.rxdata.data.model.FetchingError
import co.onlini.rxdata.data.model.FetchingState
import io.reactivex.Observable

interface VehicleDataFetcher {
    fun fetchData()
    fun getMostRecentAndSubsequentFetchingStates(): Observable<FetchingState>
    fun getSubsequentFetchingErrors(): Observable<FetchingError>
}