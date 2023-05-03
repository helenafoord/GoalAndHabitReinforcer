package com.example.goalandhabitreinforcer

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.goalandhabitreinforcer.databinding.ActivityDataBinding

class GoalAndHabitDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    private lateinit var positiveHabit: TextView
    private lateinit var task: TextView
    private lateinit var description: TextView
    private lateinit var goal: GoalAndHabitData

    companion object{
        const val EXTRA_GOAL = "goal"
        const val TAG = "GoalAndHabitListActivity"
    }
}

//val taskCompleted: Boolean?,
//    val goal: Goal
//
//):Parcelable{
//    @Parcelize
//    data class Goal(
//        val description: String?,
//    ):Parcelable
//}