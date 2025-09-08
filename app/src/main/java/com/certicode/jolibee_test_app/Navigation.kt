package com.certicode.jolibee_test_app

// In a new file, e.g., "Navigation.kt"
sealed class Screen(val route: String) {
    // Add other screens here
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object TaskList : Screen("task_list_screen")
    object TaskAdd : Screen("task_add_screen")
    object ContactList : Screen("contact_list_screen")

    object ContactAdd : Screen("contact_add_screen")




    // test screen
    object quoteScreen : Screen("quote_screen")
}