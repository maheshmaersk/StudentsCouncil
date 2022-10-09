package com.amvlabs.studentscouncil.models

import java.io.Serializable

data class UserDetail(
    val category: String,
    val description: String,
    val email: String,
    val userID: String,
    var report_description: String?,
    var report_status: Long? = 0,
    var isDeleted: Boolean? = false,
    var documentID: String?,
):Serializable