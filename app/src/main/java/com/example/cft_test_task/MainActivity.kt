package com.example.cft_test_task

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cft_test_task.database.UsersDao
import com.example.cft_test_task.databinding.ActivityMainBinding
import com.example.cft_test_task.model.User
import com.example.cft_test_task.user_info.UserInfoActivity

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: UsersAdapter

    private val usersDao: UsersDao
        get() = UsersListApp.database.usersDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, usersDao)
        adapter = UsersAdapter(
            onItemClick = { user ->
                val intent = Intent(this, UserInfoActivity::class.java).apply {
                    putExtra("user", user)
                }
                startActivity(intent)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresher.setOnRefreshListener {
            presenter.refreshUsers()
        }

        presenter.loadUsers()
    }

    override fun showLoading() {
        binding.swipeRefresher.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefresher.isRefreshing = false
    }

    override fun showUsers(usersList: List<User>) {
        adapter.submitList(usersList)
    }

    override fun showError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}