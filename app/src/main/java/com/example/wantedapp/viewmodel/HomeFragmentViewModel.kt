package com.example.wantedapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

    private  var  dao = DatabaseHelper(getApplication()).dao()




     fun addData(post : List<Post>) =viewModelScope.launch {
        dao.delete()
    }
    fun addSingleData(post : Post) = viewModelScope.launch {
        dao.insert(post)

    }

    fun fetchData() : LiveData<List<Post>>{
        return dao.getData()
    }
    fun search(query : String) :LiveData<List<Post>> {
        return dao.searchPost(query)
    }

}