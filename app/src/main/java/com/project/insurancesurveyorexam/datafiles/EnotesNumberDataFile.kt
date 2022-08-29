package com.project.insurancesurveyorexam.datafiles

data class EnotesNumberDataFile(val EnotesChapterName: String ="", val EnotesChapterContent: String ="") {

    fun getChapterName(): String {
        return EnotesChapterName
    }

    fun getChapterContent(): String{
        return EnotesChapterContent
    }

}
