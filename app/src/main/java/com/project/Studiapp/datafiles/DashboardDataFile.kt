package com.project.Studiapp.datafiles

data class DashboardDataFile(
    val PersonName: String ="",
    val Attended: String ="",
    val dashboardtestnumber: String ="",
    val dashboardtestdate: String ="",
    val dashboardtestmarks: String ="",
    val dashboardtesttime: String ="",
    val dashboardtestpassfail: String =""
) {
    fun getAttendence(): String {
        return Attended
    }
    fun getPerson(): String {
        return PersonName
    }

    fun gettestnumber(): String {
        return dashboardtestnumber
    }

    fun gettestdate(): String {
        return dashboardtestdate
    }

    fun gettestmarks(): String {
        return dashboardtestmarks
    }

    fun gettesttime(): String {
        return dashboardtesttime
    }

    fun gettestpassfail(): String{
        return dashboardtestpassfail
    }

}