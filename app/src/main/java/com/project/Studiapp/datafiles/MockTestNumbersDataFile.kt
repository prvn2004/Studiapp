package com.project.Studiapp.datafiles

data class MockTestNumbersDataFile(
    val MockTestTitle: String ="",
    val MockTestMarks: String ="",
    val MockTestDate: String ="",
    val MockTestQuestionNumber: String ="",
    val MockTestDuration: String ="",
    val Questions: MutableMap<String, Question> = mutableMapOf()
) {

    fun getTestNumber(): String {
        return MockTestTitle
    }

    fun getTestMark(): String {
        return MockTestMarks
    }

    fun getTestDate(): String {
        return MockTestDate
    }
    fun getTestDuration(): String {
        return MockTestDuration
    }
    fun getTestQuestionNumber(): String {
        return MockTestQuestionNumber
    }

}
