package com.certicode.jolibee_test_app.presentation.home

sealed class HomeScreenItems(val title: String) {
    object TaskList : HomeScreenItems("Task List")
    object Completed : HomeScreenItems("Completed")
    object ContactsPeople : HomeScreenItems("Contacts-People")
    object ContactsBusiness : HomeScreenItems("Contacts-Business")
    object Tags : HomeScreenItems("Tags")
    object Categories : HomeScreenItems("Categories")

}