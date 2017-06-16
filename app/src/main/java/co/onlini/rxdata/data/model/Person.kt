/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.model

import co.onlini.rxdata.data.storage.PersonModel

data class Person(val id: Long, val firstName: String, val lastName: String, val phone: String?) : PersonModel {
    override fun _id(): Long = id
    override fun first_name(): String = firstName
    override fun last_name(): String = lastName
    override fun phone(): String? = phone

    companion object {
        private val CREATOR = PersonModel.Creator { _id, first_name, last_name, phone ->
            Person(_id, first_name, last_name, phone)
        }
        val FACTORY = PersonModel.Factory(CREATOR)
        val MAPPER = PersonModel.Mapper(FACTORY)
    }
}