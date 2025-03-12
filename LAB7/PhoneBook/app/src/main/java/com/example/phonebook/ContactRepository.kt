package com.example.phonebook

import androidx.compose.runtime.mutableStateListOf

public object ContactRepository {
    val contactList = mutableStateListOf(
        Contact(1, "Alice", "Smith", "123456789", "123 Main St", "alice@mail.com", "linkedin.com/alice", R.drawable.ic_person),
        Contact(2, "Bob", "Johnson", "987654321", "456 Elm St", "bob@mail.com", "linkedin.com/bob", R.drawable.ic_person),
        Contact(3, "Carol", "Taylor", "555555555", "789 Pine St", "carol@mail.com", "linkedin.com/carol", R.drawable.ic_person),
        Contact(4, "David", "Lee", "111222333", "321 Oak St", "david@mail.com", "linkedin.com/david", R.drawable.ic_person),
        Contact(5, "Emma", "Clark", "444555666", "654 Maple St", "emma@mail.com", "linkedin.com/emma", R.drawable.ic_person)
    )
}