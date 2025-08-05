package com.example.cft_test_task

import com.example.cft_test_task.api.RetrofitClient
import com.example.cft_test_task.database.UsersDao
import com.example.cft_test_task.database.entities.UserEntity
import com.example.cft_test_task.model.Coordinates
import com.example.cft_test_task.model.Location
import com.example.cft_test_task.model.Timezone
import com.example.cft_test_task.model.User
import com.example.cft_test_task.model.UsersResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainPresenter(
    private val view: MainView,
    private val dao: UsersDao) : CoroutineScope by MainScope(){


    fun loadUsers() {
        view.showLoading()
        launch {
            try {
                val cached = withContext(Dispatchers.IO) {
                    dao.getSavedUsers()
                }
                if (cached.isNotEmpty()) {
                    view.hideLoading()
                    view.showUsers(cached.map { it.toUser() })
                } else {
                    loadNewUsers()
                }
            } catch (e: Exception) {
                view.hideLoading()
                view.showError("Ошибка чтения из БД: ${e.localizedMessage}")
            }
        }
    }

    fun refreshUsers() {
        view.showLoading()
        launch {
            loadNewUsers()
        }
    }

    private suspend fun loadNewUsers() {
        val response: Response<UsersResponse>
        try {
            response = withContext(Dispatchers.IO) {
                RetrofitClient.instance.getUsers(results = 15).execute()
            }
        } catch (e: Exception) {
            view.hideLoading()
            view.showError("Ошибка сети: ${e.localizedMessage}")
            return
        }

        view.hideLoading()
        if (response.isSuccessful) {
            val users = response.body()?.results.orEmpty()
            withContext(Dispatchers.IO) {
                dao.replaceAll(users.map { it.toEntity() })
            }
            view.showUsers(users)
        } else {
            view.showError("Ошибка сервера: ${response.code()}")
        }
    }

    fun onDestroy() {
        cancel()
    }
}

private fun UserEntity.toUser(): User = User(
    gender = gender,
    name = name,
    location = Location(
        street = street,
        city =city,
        state = state,
        country = country,
        postcode = postcode,
        coordinates = Coordinates(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude
        ),
        timezone= Timezone(
            offset = timezone.offset,
            description = timezone.description
        )
    ),
    email = email,
    login = login,
    dob = dob,
    registered = registered,
    phone = phone,
    cell = cell,
    id = idInfo,
    picture = picture,
    nat = nat
)

private fun User.toEntity(): UserEntity = UserEntity(
    id = 0L,
    gender = gender,
    name = name,
    street = location.street,
    city = location.city,
    state = location.state,
    country = location.country,
    postcode = location.postcode,
    coordinates = location.coordinates,
    timezone = location.timezone,
    email = email,
    login = login,
    dob = dob,
    registered = registered,
    phone = phone,
    cell = cell,
    idInfo = id,
    picture = picture,
    nat = nat
)