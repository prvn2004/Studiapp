package com.project.Studiapp.datafiles

data class Question(
    var QuestionTitle: String = "",
    val option1: String = "",
    val option2: String = "",
    val option3: String = "",
    val option4: String = "",
    val option5: String = "",
    var Answer: String = "",
    var userAnswer: String = ""
) {
    fun getTitle(): String {
        return QuestionTitle
    }

    fun getoptionFirst(): String {
        return option1
    }

    fun getoptionSecond(): String {
        return option2
    }

    fun getoptionThird(): String {
        return option3
    }

    fun getoptionFourth(): String {
        return option4
    }

    fun getCorrectAnswer(): String {
        return Answer
    }

}
