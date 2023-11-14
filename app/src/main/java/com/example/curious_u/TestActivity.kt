package com.example.curious_u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.curious_u.data.Query
import com.example.curious_u.data.Test
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// 사용자가 어떤 항목을 선택했는지 저장하는 클래스
class ChoiceViewModel : ViewModel() {
    public val _choice = MutableLiveData<Int>(0)
    val data: LiveData<Int> = _choice
    fun setData(newData: Int) {
        _choice.value = newData
    }

    fun getData(): Int? = _choice.value
}


class TestActivity : AppCompatActivity() {
    lateinit var choiceViewModel: ChoiceViewModel
    var currentIndex: Int = 0
    var isFisrtCalled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)

        val jsonTest = Json.decodeFromString<Test>(
            """
        {
    "title": "나와 닮은 제로 음료수는?",
    "answers": {
        "ISTJ": "나랑드 사이다",
        "ISFJ": "링티 제로",
        "INFJ": "티즐 제로",
        "INTJ": "슈가로로 콜라",
        "ISTP": "닥터페퍼 제로",
        "ISFP": "납작복숭아 아이스티 제로",
        "INFP": "실론티 제로",
        "INTP": "클룹제로 소다",
        "ESTP": "갈배사이다 제로",
        "ESFP": "환타 제로",
        "ENFP": "비타오백 스파클링 제로",
        "ENTP": "코카콜라 제로",
        "ESTJ": "탄산수",
        "ESFJ": "밀키스 제로",
        "ENFJ": "보성홍차 제로",
        "ENTJ": "웰치스 제로"
    },
    "queries": [
        {
            "question": "I? E?",
            "selects": [
                {
                    "text": "I", 
                    "character": "I",
                    "score": 10
                },
                { 
                    "text": "E", 
                    "character": "E",
                    "score": 10
                }
            ]
        },
        {
            "question": "S? N?",
            "selects": [
                {
                    "text": "S", 
                    "character": "S",
                    "score": 10
                },
                { 
                    "text": "N", 
                    "character": "N",
                    "score": 10
                }
            ]
        },
        {
            "question": "F? T?",
            "selects": [
                {
                    "text": "F",
                    "character": "F",
                    "score": 10
                },
                {
                    "text": "T",
                    "character": "T",
                    "score": 10
                }
            ]
        },
        {
            "question": "P? J?",
            "selects": [
                {
                    "text": "P",
                    "character": "P",
                    "score": 10
                },
                {
                    "text": "J",
                    "character": "J",
                    "score": 10
                }
            ]
        }
    ]
}
       
        """.trimIndent()
        )

        var length = jsonTest.queries.size
        Log.d("mytag", jsonTest.title)
        Log.d("mytag", jsonTest.answers.toString())
        Log.d("mytag", length.toString())

        choiceViewModel = ViewModelProvider(this).get(ChoiceViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, QueryFragment.newInstance(jsonTest.queries[currentIndex]))
            .commit()

        choiceViewModel.data.observe(this, Observer {
            if (isFisrtCalled) {
                isFisrtCalled = false
            } else {
                Log.d("mytag", "from observe : " + it.toString())
                jsonTest.queries[currentIndex].solve(it)
                currentIndex++
                if (currentIndex == jsonTest.queries.size) {
                    val intent = Intent(this, ResultActivity::class.java)
                    // intent.putExtra("result", jsonTest.getResult())
                    intent.putExtra("test", jsonTest)
                    startActivity(intent)
                } else {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        QueryFragment.newInstance(jsonTest.queries[currentIndex])
                    ).commit()
                }
            }
        })
    }
}