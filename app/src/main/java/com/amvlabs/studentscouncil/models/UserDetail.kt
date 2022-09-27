package com.amvlabs.studentscouncil.models

data class UserDetail(
    val category: String,
    val description: String,
    val email: String,
    val userID: String,
    var report_description: String?,
    var report_status: Long? = 0,
)