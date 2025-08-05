package com.example.cft_test_task

import com.example.cft_test_task.model.User

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showUsers(usersList: List<User>)
    fun showError(message: String)
}