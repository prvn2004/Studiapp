package com.project.insurancesurveyorexam.datafiles

data class UpcomingTestDataFile(
    val timestamp: Long =0,
    val MockTestDate: String = "",
    val MockTestSetNo: String = "",
    val MockTestTitle: String = "",
    val MockTestQuestionNumber: String = "",
    val MockTestTime: String = "",
    val MockTestMarks: String = "",
    val MockTestDuration: String = "",
    val Questions: MutableMap<String, Question> = mutableMapOf()
) {

    fun getTestNumber(): String {
        return MockTestTitle
    }
    fun getTimeStump(): Long {
        return timestamp
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
    fun getTestSetNo(): String{
        return MockTestSetNo
    }
    fun getTestDayTime(): String{
        return MockTestTime
    }

}
