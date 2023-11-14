package com.example.curious_u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.curious_u.data.Test

class DataViewModel : ViewModel() {
    private val _data = MutableLiveData<String>("")
    val data: LiveData<String> = _data

    fun setData(newData: String) {
        _data.value = newData
    }

    fun getData() : String? = _data.value
}

class CountViewModel : ViewModel() {
    private val _data = MutableLiveData<Int>(0)
    val data: LiveData<Int> = _data

    fun setData(newData: Int) {
        _data.value = newData
    }

    fun getData() : Int? = _data.value
}

//class TestViewModel : ViewModel() {
//    public val test = MutableLiveData<Test>(null)
//    public val currentIndex = MutableLiveData<Int>(0)
//}



class ViewModelStudyActivity : AppCompatActivity() {
    lateinit var viewModel: DataViewModel
    lateinit var countViewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_study)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        viewModel.setData("Hello")

        countViewModel = ViewModelProvider(this).get(CountViewModel::class.java)
//        Log.d("mytag", viewModel.getData()!!)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, DemoFragment()).commit()
        countViewModel.data.observe(this, Observer {
            findViewById<TextView>(R.id.counter).text = it.toString()
            Log.d("mytag", it.toString())
        })

    }

}