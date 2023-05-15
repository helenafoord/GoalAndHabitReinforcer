package com.example.goalandhabitreinforcer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.goalandhabitreinforcer.databinding.ActivityDataBinding

class GoalAndHabitDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    private lateinit var positiveHabit: TextView
    private lateinit var task: TextView
    private lateinit var description: TextView
    private lateinit var goal: GoalAndHabitData
    private var isCreatingNewGoal: Boolean = false

    companion object{
        const val EXTRA_GOAL = "goal"
        const val TAG = "GoalAndHabitListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isCreatingNewGoal = intent.getBooleanExtra(GoalAndHabitListActivity.CREATING_NEW_GOAL, false)
        if(isCreatingNewGoal){
            goal = GoalAndHabitData()
            toggleEditable()
        }else{
            intent.getParcelableExtra<GoalAndHabitData>(EXTRA_GOAL)?:GoalAndHabitData.Goal()
        }

    }

    private fun toggleEditable() {
        TODO("Not yet implemented")
    }
}

