package com.example.curious_u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.curious_u.data.Test

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)
        findViewById<TextView>(R.id.result_text).text = intent.getStringExtra("result")

        val resultImage = findViewById<ImageView>(R.id.result_image)

        // 선택된 결과에 따라 해당 이미지 설정
        // val result = intent.getStringExtra("result")
        val test = intent.getSerializableExtra("test") as Test
        val result = test.getResult()
        val mbti = test.resultToMBTI()
        Log.d("mytag", mbti)
        Log.d("mytag", result)
        when (result) {
            "ISTJ" -> resultImage.setImageResource(R.drawable.drink_istj_image)
            //"ISFJ" -> resultImage.setImageResource(R.drawable.drink_isfj_image)
            // 나머지 음료수에 대한 이미지도 추가
            // "INFJ" -> resultImage.setImageResource(R.drawable.infj_image)
            // ...

            else -> resultImage.setImageResource(R.drawable.drink_default_image) // 기본 이미지 설정
        }
    }
}