/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.model

enum class FetchingError {
    UNAUTHORIZED,
    CONNECTION_ISSUE,
    INVALID_RESPONSE,
    INTERNAL_ERROR,
    OTHER
}