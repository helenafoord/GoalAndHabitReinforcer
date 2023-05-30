package com.example.goalandhabitreinforcer
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Goal(
    var goal: String = " ",
    var purpose: String = " ",
    var goalCompleted: Boolean = false,
    var tasks: Int = 0,
    var tasksCompleted: Int = 0,
    var ownerId: String = "",
    var objectId: String = ""

):Parcelable{
    fun tasksRemaining(): Int{
        return tasks - tasksCompleted
    }
}
