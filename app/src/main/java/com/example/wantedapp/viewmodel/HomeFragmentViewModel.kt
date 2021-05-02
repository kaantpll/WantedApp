package com.example.wantedapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wantedapp.database.DatabaseHelper
import com.example.wantedapp.database.PostDao
import com.example.wantedapp.models.Post
import com.example.wantedapp.util.CustomSharedPreferences
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application)  : BaseViewModel(application) {


    private val postList= MutableLiveData<List<Post>>()

    private val disposable = CompositeDisposable()

    private  var  dao = DatabaseHelper(getApplication()).dao()

    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    fun refreshData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {

        }

    }

     fun addData(post : List<Post>) =viewModelScope.launch {
        dao.delete()
         delay(2000)
        dao.insertAll(post)

    }

    fun fetchData() : LiveData<List<Post>>{
        return dao.getData()
    }
    fun search(query : String) :LiveData<List<Post>> {
        return dao.searchPost(query)
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}