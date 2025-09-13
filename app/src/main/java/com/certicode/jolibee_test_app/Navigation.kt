package com.certicode.jolibee_test_app

// In a new file, e.g., "Navigation.kt"
sealed class Screen(val route: String) {
    // Add other screens here
    object Login : Screen("login_screen")
    object Registration : Screen("registration_screen")
    object Home : Screen("home_screen")
    object TaskList : Screen("task_list_screen")
    object TaskAdd : Screen("task_add_screen")
    object ContactListPeople : Screen("contact_list_people_screen")
    object ContactListBusiness : Screen("contact_list_business_screen")
    object ContactAddPeople : Screen("contact_add_people_screen")

    object ContactUpdatePeople : Screen("contact_update_people_screen")
    object ContactAddBusiness : Screen("contact_add_business_screen")

    object TagList : Screen("tag_list_screen")
    object TagUpdateScreen : Screen("tag_update_screen")
    object TagAdd : Screen("tag_add_screen")

    object CategoryList : Screen("category_list_screen")

    object CategoryUpdateScreen : Screen("category_update_screen")
    object CategoryAdd : Screen("category_add_screen")

    // test screen
    object quoteScreen : Screen("quote_screen")
}