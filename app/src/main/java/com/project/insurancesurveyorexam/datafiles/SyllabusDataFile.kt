package com.project.insurancesurveyorexam.datafiles

data class SyllabusDataFile(
    val SyllabusChapterName: String = "",
    val SyllabusChapterContent: String = ""
) {

    fun getChapterName(): String {
        return SyllabusChapterName
    }

    fun getChapterContent(): String {
        return SyllabusChapterContent
    }

}
