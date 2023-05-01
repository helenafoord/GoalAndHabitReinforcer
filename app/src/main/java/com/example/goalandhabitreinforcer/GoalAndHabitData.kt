package com.example.goalandhabitreinforcer
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalAndHabitData(
    val positiveHabit: String?,
    val task: String?,
    val taskCompleted: Boolean?,
    val goal: Goal

):Parcelable{
    @Parcelize
    data class Goal(
        val description: String?,
    ):Parcelable
}
