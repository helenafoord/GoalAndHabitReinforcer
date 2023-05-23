package com.example.goalandhabitreinforcer
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalAndHabitData(
    val goal: String = " ",
    val purpose: String = " ",
    val goalCompleted: Boolean = false,
    val tasks: Int = 0,
    val tasksCompleted: Int = 0

):Parcelable{
    fun tasksRemaining(): Int{
        return tasks - tasksCompleted
    }
}

