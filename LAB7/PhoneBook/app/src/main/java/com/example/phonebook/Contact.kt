package com.example.phonebook

data class Contact(
    val id: Int,
    var name: String,
    var surname: String,
    var phone: String,
    var address: String,
    var email: String,
    var linkedin: String,
    val imageRes: Int
)