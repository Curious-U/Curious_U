package com.example.curious_u.data

import android.util.Log
import kotlinx.serialization.Serializable
import java.lang.IllegalArgumentException

enum class MBTICharacter {
    I, E, S, N, F, T, P, J
}

// 선택지(선택 텍스트, 엠비티아이, 점수)
@Serializable
data class Select(var text: String, var character: MBTICharacter, var score: Int) : java.io.Serializable

// 질문, 선택지(리스트)
// 질문 하나, 선택지(선택 텍스트, 엠비티아이, 점수) 여러 개
@Serializable
data class Query(var question: String, var selects: List<Select>) : java.io.Serializable {
    var completed : Boolean = false
    var selected : Select? = null

    fun solve(position: Int) {
        // 잘못된 선택지를 눌렀을 때 (물론 이런 일 없겠지만 혹시나를 대비해서 있는 거)
        if(position >= selects.size) throw IllegalArgumentException("잘못된 숫자입니다.")
        selected = selects[position] // 선택한 선택지를 selected 변수에 넣음
        completed = true // 해당 문항을 풀었다는 것을 표시
    }
}



// 테스트 이름, 문제 전체(리스트)
@Serializable
data class Test(val title: String, val queries: List<Query>, var answers : Map<String, String>) : java.io.Serializable {
    /*
    val result : MutableMap<MBTICharacter, Int> = mutableMapOf()

    init {
        result[MBTICharacter.I] = 0
        result[MBTICharacter.E] = 0
        result[MBTICharacter.S] = 0
        result[MBTICharacter.N] = 0
        result[MBTICharacter.F] = 0
        result[MBTICharacter.T] = 0
        result[MBTICharacter.P] = 0
        result[MBTICharacter.J] = 0
    }
    */

    fun resultToMBTI() : String {
        val result : MutableMap<MBTICharacter, Int> = mutableMapOf()
        result[MBTICharacter.I] = 0
        result[MBTICharacter.E] = 0
        result[MBTICharacter.S] = 0
        result[MBTICharacter.N] = 0
        result[MBTICharacter.F] = 0
        result[MBTICharacter.T] = 0
        result[MBTICharacter.P] = 0
        result[MBTICharacter.J] = 0

        for(q in queries) {
            if(q.completed) {
                result[q.selected!!.character] = result[q.selected!!.character]!! + q.selected!!.score
            } else {
                throw IllegalStateException("모든 답안을 작성하지 않았습니다.")
            }
        }

        var mbti = ""

        mbti += if(result[MBTICharacter.I]!! > result[MBTICharacter.E]!!) "I" else "E"
        mbti += if(result[MBTICharacter.S]!! > result[MBTICharacter.N]!!) "S" else "N"
        mbti += if(result[MBTICharacter.F]!! > result[MBTICharacter.T]!!) "F" else "T"
        mbti += if(result[MBTICharacter.P]!! > result[MBTICharacter.J]!!) "P" else "J"

        return mbti
    }

    fun getResult() : String = answers[resultToMBTI()]!!

    fun setAnswer(answers : Map<String, String>) {
        this.answers = answers
    }
}