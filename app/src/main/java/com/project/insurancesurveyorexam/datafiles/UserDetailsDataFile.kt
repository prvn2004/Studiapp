package com.project.insurancesurveyorexam.datafiles

data class UserDetailsDataFile( val personName: String, val personEmail: String) {

    fun getName(): String {
        return personName
    }

    fun getEmail(): String{
        return personEmail
    }

}
